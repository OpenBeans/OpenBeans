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
package ro.emilianbold.notifications;

import java.awt.SystemTray;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;
import ro.emilianbold.notifications.linux.LinuxNotificationDisplayer;
import ro.emilianbold.notifications.linux.jna.LinuxNotifyBridge;
import ro.emilianbold.notifications.macos.MacOSNotificationDisplayer;
import ro.emilianbold.notifications.tray.SystemTrayNotificationDisplayer;

@ServiceProvider(
        service = NotificationDisplayer.class,
        position = 10)
public final class DelegatingNotificationDisplayer extends NotificationDisplayer {

    private NotificationDisplayer delegate;

    public DelegatingNotificationDisplayer() {
    }

    protected NotificationDisplayer getDelegate() {
        if (delegate == null) {
            if (Utilities.isMac()) {
                MacOSNotificationDisplayer macOSDisplayer = new MacOSNotificationDisplayer();
                if (macOSDisplayer.isValid()) {
                    delegate = macOSDisplayer;
                } else {
                    delegate = getFallbackDisplayer();
                }
            } else if (Utilities.getOperatingSystem() == Utilities.OS_LINUX && LinuxNotifyBridge.getDefault().init().isValid()) {
                delegate = new LinuxNotificationDisplayer();
            } else if (SystemTray.isSupported()) {
                delegate = new SystemTrayNotificationDisplayer(SystemTray.getSystemTray());
            } else {
                delegate = getFallbackDisplayer();
            }
        }

        return delegate;
    }
    
    public static NotificationDisplayer getFallbackDisplayer() {
        boolean found = false;
        for (NotificationDisplayer d : Lookup.getDefault().lookupAll(NotificationDisplayer.class)) {
            if (d instanceof DelegatingNotificationDisplayer) {
                found = true;
            } else if (found) {
                //this is the next one after this
                return d;
            }
        }

        return null;
    }

    @Override
    public Notification notify(String title, Icon icon, String details, ActionListener listener, Priority p) {
        return getDelegate().notify(title, icon, details, listener, p);
    }

    @Override
    public Notification notify(String title, Icon icon, JComponent jc, JComponent jc1, Priority p) {
        return getDelegate().notify(title, icon, jc, jc1, p);
    }

}
