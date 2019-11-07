/**
 * Copyright (c) 2018, 2019 Emilian Marius Bold
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package ro.emilianbold.notifications.macos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;

public class TerminalNotifier {

    private final static Logger log = Logger.getLogger(TerminalNotifier.class.getName());

    private static final int TIMEOUT_SECONDS = 10;

    private final ExecutorService processResultReaders = Executors.newCachedThreadPool();
    private final ExecutorService async = Executors.newCachedThreadPool();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(String prop, PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(prop, pcl);
    }

    public void sendNotification(final String notificationName, final String title, final String details, final String uuid, final boolean hasListener) {
        async.submit(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("Displaying macOS notification " + notificationName);
                show(notificationName, title, details, uuid, hasListener);
            }
        });
    }

    private File getTerminalNotifierBinary() {
        File executable = InstalledFileLocator.getDefault().locate("terminal-notifier-1.7.1/terminal-notifier.app/Contents/MacOS/terminal-notifier", //NOI18N
                "ro.emilianbold.notifications", //NOI18N
                false);

        if (executable == null) {
            return null;
        }

        Path executablePath = executable.toPath();

        if (!Files.isExecutable(executablePath)) {
            try {
                Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>(Files.getPosixFilePermissions(executablePath));
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                Files.setPosixFilePermissions(executablePath, perms);
            } catch (IOException ex) {
                log.log(Level.WARNING, "Cannot set executable bit on terminal-notifier, notifications cannot work", ex);
                return null;
            }
        }

        return executable;
    }

    private void show(final String notificationName, String title, String details, final String uuid, final boolean hasListener) {
        File executable = getTerminalNotifierBinary();

        if (executable == null) {
            log.warning("Cannot find terminal-notifier binary");
            return;
        }

        File appParent = executable.getParentFile().getParentFile().getParentFile();

        List<String> commandLine = new ArrayList<String>();
        ProcessBuilder pb = new ProcessBuilder(commandLine);

        commandLine.addAll(Arrays.asList(executable.getPath(),
                "-title", title, //NOI18N
                "-message", details)); //NOI18N

        if (hasListener) {
            commandLine.add("-timeout"); //NOI18N
            commandLine.add(String.valueOf(TIMEOUT_SECONDS));
        }

        //TODO: use non-null image for -contentImage
        pb.directory(appParent);

        final Process process;
        
        try {
            process = pb.start();
        } catch (IOException ioe) {
            Exceptions.printStackTrace(ioe);
            return;
        }

        Future<String> future = processResultReaders.submit(new Callable<String>() {
            @Override
            public String call() {
                Thread.currentThread().setName("Waiting on macOS notification " + notificationName);

                if (hasListener) {
                    try {
                        String result = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine();
                        return result;
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                        //do nothing, assume it finished normally
                    }
                    process.destroy();
                } else {
                    try {
                        //without a -timeout the process should exit immediatelly
                        process.waitFor();
                    } catch (InterruptedException ie) {
                        log.info("Interrupted while waiting for terminal-notifier process to finish");
                    }
                }

                return null;
            }

        });

        try {
            String result = future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (!result.isEmpty()) {
                pcs.firePropertyChange(result, null, uuid);
            }
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ExecutionException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TimeoutException ex) {
            future.cancel(true);
        } finally {
            process.destroy();
        }
    }

    public boolean isValid() {
        return getTerminalNotifierBinary() != null;
    }
}
