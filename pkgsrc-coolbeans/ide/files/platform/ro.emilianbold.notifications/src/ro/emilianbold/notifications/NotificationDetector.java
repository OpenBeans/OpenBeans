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

import java.util.Arrays;
import java.util.List;

public class NotificationDetector {
    private final static NotificationDetector INSTANCE = new NotificationDetector();
    
    public static NotificationDetector getDefault() {
        return INSTANCE;
    }
    
    private NotificationDetector() {
        //nothing
    }
    
    //TODO: I18N
    private final List<String> notificationNames = Arrays.asList("Hello from NBnotify", "Slowness detected",
            "Updates found", "Build", "Notification");
    
    private final int HELLO_NOTIFICATION_ID = 0;
    private final int SLOWNESS_NOTIFICATION_ID = 1;
    private final int UPDATES_NOTIFICATION_ID = 2;
    private final int BUILD_NOTIFICATION_ID = 3;
    private final int GENERIC_NOTIFICATION_ID = 4;
    
    public String getNotificationName(){
        return notificationNames.get(getNotificationID());
    }
    
    private int getNotificationID(){
        Exception ex = new Exception();
        
//        {
//            IOProvider io = Lookup.getDefault().lookup(IOProvider.class);
//            InputOutput output =  io.getIO("growl", true);
//            StringWriter sw = new StringWriter();
//            ex.printStackTrace(new PrintWriter(sw));
//            output.getOut().write(sw.toString());
//        }
        
        for(StackTraceElement stack : ex.getStackTrace()){
            if(stack.getClassName().equals("org.netbeans.modules.uihandler.SlownessReporter")){ //NOI18N
                return SLOWNESS_NOTIFICATION_ID;
            }
            //TODO: There are other uses in the org.netbeans.modules.autoupdate.ui package.
            if(stack.getClassName().equals("org.netbeans.modules.autoupdate.ui.actions.AutoupdateCheckScheduler")){ //NOI18N
                return UPDATES_NOTIFICATION_ID;
            }
            if(stack.getClassName().equals("org.netbeans.spi.project.ui.support.BuildExecutionSupport")){
                return BUILD_NOTIFICATION_ID;
            }
        }
        
        return GENERIC_NOTIFICATION_ID;
    }

}
