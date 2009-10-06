/**
 * $Id$
 * $URL$
 * *************************************************************************
 * Copyright (c) 2002-2009, DuraSpace.  All rights reserved
 * Licensed under the DuraSpace Foundation License.
 *
 * A copy of the DuraSpace License has been included in this
 * distribution and is available at: http://scm.dspace.org/svn/repo/licenses/LICENSE.txt
 */
package org.dspace.services.events;

import java.util.List;
import java.util.Vector;

import org.dspace.services.model.Event;
import org.dspace.services.model.EventListener;

/**
 * This is a sample event listener for testing,
 * it does no filtering
 * 
 * @author Aaron Zeckoski (azeckoski@gmail.com) - azeckoski - 1:17:31 PM Nov 20, 2008
 */
public class EventListenerNoFilter implements EventListener {

    public List<Event> received = new Vector<Event>();
    public List<Event> getReceivedEvents() {
        return received;
    }
    public void clearReceivedEvents() {
        received.clear();
    }

    /* (non-Javadoc)
     * @see org.dspace.services.model.EventListener#getEventNamePrefixes()
     */
    public String[] getEventNamePrefixes() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.dspace.services.model.EventListener#getResourcePrefix()
     */
    public String getResourcePrefix() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.dspace.services.model.EventListener#receiveEvent(org.dspace.services.model.Event)
     */
    public void receiveEvent(Event event) {
        received.add(event);
    }

}
