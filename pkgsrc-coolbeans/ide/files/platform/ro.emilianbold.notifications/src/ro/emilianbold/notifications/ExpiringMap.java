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

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpiringMap {
    //TODO: synchronization to these maps
    private final HashMap<String, ActionListener> actions = new HashMap<String, ActionListener>();
    private final HashMap<String, Long> uuidTimestamp = new HashMap<String, Long>();
    private final long timeout;
    
    public ExpiringMap(long timeout) {
        this.timeout = timeout;
    }

    public void put(String uuid, ActionListener al) {
        actions.put(uuid, al);
        uuidTimestamp.put(uuid, System.currentTimeMillis());
        cleanMaps();
    }
   
    //clean actionlisteners older than N seconds
    private void cleanMaps() {
        long now = System.currentTimeMillis();
        List<String> toDelete = new ArrayList<String>();
        for (Map.Entry<String, Long> entry : new ArrayList<Map.Entry<String, Long>>(uuidTimestamp.entrySet())) {
            long timestamp = entry.getValue();

            if (timestamp + timeout < now) {
                toDelete.add(entry.getKey());
            }
        }
        for (String key : toDelete) {
            uuidTimestamp.remove(key);
            actions.remove(key);
        }
    }

    public ActionListener remove(String context) {
        ActionListener al = actions.remove(context);
        uuidTimestamp.remove(context);
        cleanMaps();
        
        return al;
    }
    
}
