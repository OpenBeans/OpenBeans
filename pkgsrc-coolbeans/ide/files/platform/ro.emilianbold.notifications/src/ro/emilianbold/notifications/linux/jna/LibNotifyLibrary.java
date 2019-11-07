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
package ro.emilianbold.notifications.linux.jna;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface LibNotifyLibrary extends Library {

    public static enum gboolean {

        FALSE,
        TRUE
    }

    //TODO: Callback doesn't seem to work
    public interface NotifyActionCallback extends Callback {
//            void
//            (*NotifyActionCallback) (NotifyNotification *notification,
//                                     char *action,
//                                     gpointer user_data);

        void callback(Pointer notification, String action, Pointer user_data);
    }

    //gboolean notify_init (const char *app_name);
    gboolean notify_init(String appName);

    //void notify_uninit (void);
    void notify_uninit();

//        NotifyNotification *
//        notify_notification_new (const char *summary,
//                                 const char *body,
//                                 const char *icon);
    Pointer notify_notification_new(String summary, String body, String icon);

//        gboolean
//        notify_notification_show (NotifyNotification *notification,
//                                  GError **error);        
    gboolean notify_notification_show(Pointer notification, Pointer error);

//        gboolean
//        notify_notification_close (NotifyNotification *notification,
//                                   GError **error);
    gboolean notify_notification_close(Pointer notification, Pointer error);

//        gboolean
//        notify_notification_update (NotifyNotification *notification,
//                                    const char *summary,
//                                    const char *body,
//                                    const char *icon);        
    gboolean notify_notification_update(Pointer notification, String summary, String body, String icon);

//        void                notify_notification_set_timeout           (NotifyNotification *notification,
//                                                                       gint                timeout);
    void notify_notification_set_timeout(Pointer notification, int timeout);

//        void
//        notify_notification_clear_actions (NotifyNotification *notification);
    void notify_notification_clear_actions(Pointer notification);

//        void
//        notify_notification_add_action (NotifyNotification *notification,
//                                        const char *action,
//                                        const char *label,
//                                        NotifyActionCallback callback,
//                                        gpointer user_data,
//                                        GFreeFunc free_func);
    void notify_notification_add_action(Pointer notification, String action, String label, NotifyActionCallback callback,
            Pointer user_data, Object free_func);

//        void
//        g_object_unref (gpointer object);        
    void g_object_unref(Pointer object);

//EMI: properties could be accessed via notify_notification_get_property
}
