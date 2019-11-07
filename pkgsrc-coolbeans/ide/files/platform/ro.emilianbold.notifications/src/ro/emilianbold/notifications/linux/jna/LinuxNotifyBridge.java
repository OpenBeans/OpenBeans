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

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.EnumConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinuxNotifyBridge {

    public static LinuxNotifyBridge instance = new LinuxNotifyBridge();

    private LibNotifyLibrary lib;
    private Pointer notification;
    private boolean initialized = false;

    public static LinuxNotifyBridge getDefault() {
        return instance;
    }

    private LinuxNotifyBridge() {

    }

    public void show(String title, String details) {
        init();
        if (!isValid()) {
            return;
        }

        lib.notify_notification_update(notification, title, details, null);
        lib.notify_notification_set_timeout(notification, -1);
        lib.notify_notification_clear_actions(notification);
        lib.notify_notification_show(notification, null);
    }

    public LinuxNotifyBridge init() {
        return init("NBnotify");
    }

    public boolean isValid() {
        init();

        return lib != null;
    }

    public LinuxNotifyBridge init(String applicationName) {
        if (initialized) {
            return this;
        }

        Map<String, Object> options = new HashMap<String, Object>();
        options.put(Library.OPTION_TYPE_MAPPER, new DefaultTypeMapper() {
            {
                addTypeConverter(LibNotifyLibrary.gboolean.class, new EnumConverter(LibNotifyLibrary.gboolean.class));
            }
        });
        try {
            lib = (LibNotifyLibrary) Native.loadLibrary("libnotify", LibNotifyLibrary.class, options); //NOI18N

            lib.notify_init(applicationName);

            notification = lib.notify_notification_new("Hello", "Hello world!", null);
        } catch (UnsatisfiedLinkError ule) {
            Logger.getLogger(LinuxNotifyBridge.class.getName()).log(Level.SEVERE, "Linux distro without libnotify. Native notifications won't work.", ule);
        }
        initialized = true;
        
        return this;
    }

}
