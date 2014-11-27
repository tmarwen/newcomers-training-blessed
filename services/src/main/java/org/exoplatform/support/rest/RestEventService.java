package org.exoplatform.support.rest;

import com.google.gson.Gson;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.support.jcr.services.EventService;
import org.exoplatform.support.model.beans.Event;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.RuntimeDelegate;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/11/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
@Path("/events-manager")
public class RestEventService implements ResourceContainer
{

    private static final Log LOG = ExoLogger.getExoLogger("org.exoplatform.support.rest.RestEventService");

    private static final CacheControl cacheControl;

    private EventService eventService;

    static
    {
        RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
        cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        cacheControl.setNoStore(true);
    }

    public RestEventService(EventService eventService)
    {
        this.eventService = eventService;
        LOG.info(eventService);
    }

    @GET
    @Path("/add/{name}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEvent (@Context UriInfo uriInfo,
                               @PathParam("name") String name,
                               @PathParam("date") String date)
    {
        eventService.create(name, date);
        List<Event> eventList = eventService.list();
        return Response.created(uriInfo.getAbsolutePath())
                .entity(eventList)
                .type(MediaType.APPLICATION_JSON_TYPE + "; charset=utf-8")
                .status(Response.Status.OK)
                .build();
    }

    @GET
    @Path("/remove/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeEvent (@PathParam("name") String name)
    {
        if (eventService.delete(name))
        {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listEvents(@Context UriInfo uriInfo)
    {
        List<Event> eventList = eventService.list();
        Gson gson = new Gson();
        return gson.toJson(eventList);
    }
}
