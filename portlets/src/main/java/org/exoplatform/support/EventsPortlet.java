package org.exoplatform.support;

/**
 * Created by eXo Platform MEA on 26/11/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.support.jcr.services.EventService;
import org.exoplatform.support.model.beans.Event;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

public class EventsPortlet extends GenericPortlet
{
    private static final String EVENTS_VIEW = "/events.jsp";
    private static final String EVENTS_JSON_VIEW = "/eventsJSONList.jsp";
    private EventService eventService;

    private PortletRequestDispatcher eventsView;
    private PortletRequestDispatcher eventsJSONifiedView;

    public void render (RenderRequest request, RenderResponse response) throws PortletException, IOException
    {
        if ("true".equals(request.getParameter("isJSONList")))
        {
            eventsJSONifiedView.include(request, response);
        }
        else
        {
            doView(request, response);
        }
    }

    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException
    {
        String name = request.getParameter("event-name");
        String date = request.getParameter("event-date");

        eventService.create(name, date);
    }

    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException
    {
        List<Event> events = eventService.list();
        request.setAttribute("events", events);
        eventsView.include(request, response);
    }

    public void init(PortletConfig config) throws PortletException
    {
        super.init(config);

        // Init the events page dispatcher
        eventsView = config.getPortletContext().getRequestDispatcher(EVENTS_VIEW);
        eventsJSONifiedView = config.getPortletContext().getRequestDispatcher(EVENTS_JSON_VIEW);

        // Load the EventsService component using exo container
        ExoContainer container = ExoContainerContext.getCurrentContainer();
        eventService = container.getComponentInstanceOfType(EventService.class);
    }

    public void destroy()
    {
        eventsView = null;
        eventsJSONifiedView = null;
        super.destroy();
    }

}
