package org.exoplatform.support;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.exoplatform.support.beans.Event;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyPortlet extends GenericPortlet
{

    private static final String EVENTS_VIEW = "/events.jsp";
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static List<Event> eventList = new ArrayList<Event>();

    private PortletRequestDispatcher eventsView;

    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException
    {
        String name = request.getParameter("event-name");
        String date = request.getParameter("event-date");
        Date parsedDated = null;
        try
        {
            parsedDated = formatter.parse(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((parsedDated != null ? parsedDated : new Date()));
        eventList.add(new Event(name, calendar));
    }

    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException
    {
        request.setAttribute("events", eventList);
        eventsView.include(request, response);
    }

    public void init(PortletConfig config) throws PortletException
    {
        super.init(config);
        eventsView = config.getPortletContext().getRequestDispatcher(EVENTS_VIEW);
        eventList.add(new Event("event1", Calendar.getInstance()));
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        eventList.add(new Event("event2", Calendar.getInstance()));
    }

    public void destroy()
    {
        eventsView = null;
        eventList = null;
        super.destroy();
    }

}
