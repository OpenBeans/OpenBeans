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
package ro.emilianbold.notifications.linux;

import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.NbBundle;
import ro.emilianbold.notifications.DelegatingNotificationDisplayer;
//import org.openide.util.lookup.ServiceProvider;
import ro.emilianbold.notifications.linux.jna.LinuxNotifyBridge;

//@ServiceProvider(
//        service = NotificationDisplayer.class,
//        position = 50)
public class LinuxNotificationDisplayer extends NotificationDisplayer {

    @Override
    public Notification notify(String title, Icon icon, String details, ActionListener al, Priority p) {
        LinuxNotifyBridge.getDefault().show(title, details);

        if (al != null) {
            NotificationDisplayer d = DelegatingNotificationDisplayer.getFallbackDisplayer();
            if (d != null) {
                d.notify(title, icon, details, al, p);
            }
        }

        return new Notification() {
            @Override
            public void clear() {
                //TODO:
            }
        };
    }

    @Override
    public Notification notify(String title, Icon icon, JComponent balloonDetails, JComponent popupDetails, Priority p) {
        Notification n = notify(title, icon, NbBundle.getMessage(LinuxNotificationDisplayer.class, "customUI_message"), null, p);

        NotificationDisplayer d = DelegatingNotificationDisplayer.getFallbackDisplayer();
        if (d != null) {
            return d.notify(title, icon, balloonDetails, popupDetails, p);
        }

        return n;
    }

}
