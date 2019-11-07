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
package ro.emilianbold.notifications.tray;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import ro.emilianbold.notifications.DialogDisplayerActionListener;

public class SystemTrayNotificationDisplayer extends NotificationDisplayer {

    private final TrayIcon trayIcon;
    private ActionListener previousActionListener;
    private String command;

    public SystemTrayNotificationDisplayer(SystemTray systemTray) {
        this(ensureTrayIcon(systemTray));
    }

    public SystemTrayNotificationDisplayer(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() != null && e.getActionCommand().equals(command) && previousActionListener != null) {
                    previousActionListener.actionPerformed(e);
                }
            }
        });
    }

    private static TrayIcon ensureTrayIcon(SystemTray systemTray) {
        TrayIcon[] icons = systemTray.getTrayIcons();
        if (icons.length > 0) {
            return icons[0];
        }

        Dimension trayIconSize = systemTray.getTrayIconSize();
        int dim = Math.max(trayIconSize.width, trayIconSize.height);
        final String file;
        if (dim <= 16) {
            file = "frame.gif"; //NOI18N
        } else if (dim <= 32) {
            file = "frame32.gif"; //NOI18N
        } else {
            file = "frame48.gif"; //NOI18N
        }

        TrayIcon icon = new TrayIcon(ImageUtilities.loadImage(
                "org/netbeans/core/startup/" + file, false)); //NOI18N
        icon.setImageAutoSize(true);

        try {
            systemTray.add(icon);
        } catch (AWTException ex) {
            Exceptions.printStackTrace(ex);
        }

        return icon;
    }

    private TrayIcon.MessageType forPriority(Priority p) {
        switch (p) {
            case HIGH:
            case NORMAL:
            case LOW:
                return TrayIcon.MessageType.INFO;
            case SILENT:
            default:
                return TrayIcon.MessageType.NONE;
        }
    }

    @Override
    public Notification notify(String title, Icon icon, String details, ActionListener al, Priority p) {
        trayIcon.displayMessage(title, details, forPriority(p));

        if (al != null) {
            command = UUID.randomUUID().toString();
            trayIcon.setActionCommand(command);
        }
        previousActionListener = al;

        return new Notification() {
            @Override
            public void clear() {
                //TODO:
            }
        };
    }

    @Override
    public Notification notify(final String title, Icon icon, JComponent balloonDetails, final JComponent popupDetails, Priority p) {
        return notify(title, icon, NbBundle.getMessage(SystemTrayNotificationDisplayer.class, "customUI_message"), new DialogDisplayerActionListener(title, popupDetails), p);
    }

}
