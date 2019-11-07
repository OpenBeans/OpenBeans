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

import java.awt.Component;
import java.awt.Container;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.logging.Logger;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;

public class TestListener {
    private final static Logger logger = Logger.getLogger(TestListener.class.getName());

    private void findResultBar(Component c) {
        logger.info("TopComponent ComponentEvent" + c);

        if (c.getClass().getName().equals("org.netbeans.modules.gsf.testrunner.ResultBar")) {

            //TODO: must be in ResultPanelTree.displayReport

            try {
                //private float passedPercentage
                Field passedPercentageF = c.getClass().getDeclaredField("passedPercentage");
                passedPercentageF.setAccessible(true);

                String perc = String.valueOf(passedPercentageF.get(c));

                NotificationDisplayer.getDefault().notify("Test results", null, perc, null);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                findResultBar(child);
            }
        }
    }

    public static void registerTestListener() {
        final TestListener listener = new TestListener();
        
        TopComponent.getRegistry().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (TopComponent.Registry.PROP_TC_OPENED.equals(pce.getPropertyName())) {
                    final TopComponent tc = (TopComponent) pce.getNewValue();

                    logger.info("TopComponent opened" + tc);

                    if (tc.getClass().getName().equals("org.netbeans.modules.gsf.testrunner.api.ResultWindow")) {
                        logger.info("TopComponent ResultWindow adding listener");

                        tc.addHierarchyListener(new HierarchyListener() {

                            @Override
                            public void hierarchyChanged(HierarchyEvent he) {
                                listener.findResultBar(tc);
                            }
                        });
                    }
                }
            }
        });
    }
}
