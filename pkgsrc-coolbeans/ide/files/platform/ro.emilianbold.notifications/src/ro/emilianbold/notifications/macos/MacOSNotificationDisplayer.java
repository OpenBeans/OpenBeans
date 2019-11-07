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

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.NbBundle;
import ro.emilianbold.notifications.DialogDisplayerActionListener;
import ro.emilianbold.notifications.ExpiringMap;
import ro.emilianbold.notifications.NotificationDetector;

//@ServiceProvider(service=NotificationDisplayer.class)
public class MacOSNotificationDisplayer extends NotificationDisplayer {
     //We don't receive the event that a given notification has closed so we don't know how long to keep
    //our listeners around. 10 seconds sounds about right.

    public final int MAX_ACTIONS_LISTENER_AGE_SECONDS = 10;
    private TerminalNotifier notifier = new TerminalNotifier();
    private ExpiringMap ongoingNotifications = new ExpiringMap(MAX_ACTIONS_LISTENER_AGE_SECONDS * 1000);
    
    public MacOSNotificationDisplayer() {
        

        notifier.addPropertyChangeListener("@CONTENTCLICKED", new PropertyChangeListener() { //NOI18N
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                final String context = (String) evt.getNewValue();

                //NOTE: This runs in the "AWT-AppKit" thread which could deadlock.
                EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
//                        System.out.println("Notification was clicked for context "+context);
                        
                        ActionListener al = ongoingNotifications.remove(context);
                        if (al != null) {
                            //action is performed last otherwise we could (HOW?) get into a cycle
                            al.actionPerformed(null);
                        }
                    }
                });
            }
        });
    }

    @Override
    public Notification notify(String title, Icon icon, String details, final ActionListener al, Priority prio) {
        String notificationName = NotificationDetector.getDefault().getNotificationName();

            String uuid = UUID.randomUUID().toString();
            if (al != null) {
                ongoingNotifications.put(uuid, al);
            }

            notifier.sendNotification(notificationName, title, details, uuid, al != null);
        //TODO: Action litener, icon, priority

        return new Notification() {

            @Override
            public void clear() {
                //TODO: Hide DialogDisplayer if any is present ? Doesn't make sense as it's an user-initiated action...
                Logger.getLogger(getClass().getName()).log(Level.INFO, "Notification can't be cleared but got called to do it.", new Exception()); //NOI18N
            }
        };
    }

    @Override
    public Notification notify(final String title, Icon icon, JComponent balloonDetails, final JComponent popupDetails, Priority prt) {
        return notify(title, icon, NbBundle.getMessage(MacOSNotificationDisplayer.class, "customUI_message"), new DialogDisplayerActionListener(title, popupDetails));
    }

    public boolean isValid() {
        return notifier.isValid();
    }
}
