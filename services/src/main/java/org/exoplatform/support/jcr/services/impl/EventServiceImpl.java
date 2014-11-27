package org.exoplatform.support.jcr.services.impl;

import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.PropertiesParam;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.config.RepositoryConfigurationException;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.support.jcr.services.EventService;
import org.exoplatform.support.model.beans.Event;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by eXo Platform MEA on 26/11/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class EventServiceImpl implements EventService
{

    private static final Log LOG = ExoLogger.getExoLogger("org.exoplatform.support.jcr.services.impl.EventService");
    private RepositoryService repositoryService;
    private Node eventsNode;
    private static String EVENTS_ROOT_NODE_NAME;
    private static final String EVENT_NODE_TYPE = "exo:event";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public EventServiceImpl(RepositoryService repositoryService, InitParams initParams)
    {
        this.repositoryService = repositoryService;
        PropertiesParam propertiesParam = initParams.getPropertiesParam("events.service.config");
        if (propertiesParam != null)
        {
            EVENTS_ROOT_NODE_NAME = propertiesParam.getProperty("eventsHomeNode");
        }
        else
        {
            EVENTS_ROOT_NODE_NAME = "eventsHome";
        }
        initSessionConfig();
    }

    @Override
    public void create(String name, String date)
    {
        try
        {
            Node eventNode = eventsNode.addNode(name, EVENT_NODE_TYPE);
            eventNode.setProperty("exo:eventName", name);
            eventNode.setProperty("exo:eventDate", date);
            eventsNode.getSession().save();

        } catch (RepositoryException e)
        {
            LOG.error("Error while creating event with name " + name, e);
        }
    }

    @Override
    public boolean delete(String name)
    {
        try
        {
            eventsNode.getNode(name).remove();
            eventsNode.getSession().save();
            return true;

        } catch (RepositoryException e)
        {
            LOG.error("Error while deleting event with name " + name, e);
            return false;
        }
    }

    @Override
    public void update(String name, String newName, Calendar newDate)
    {
        try
        {
            Node node = eventsNode.getNode(name);
            node.setProperty("exo:eventName", newName);
            node.setProperty("exo:eventDate", dateFormat.format(newDate.getTime()));
            eventsNode.getSession().save();

        } catch (RepositoryException e)
        {
            LOG.error("Error while updating event with name " + name, e);
        }
    }

    @Override
    public Event get(String name)
    {
        try
        {
            Node node = eventsNode.getNode(name);
            String eventName = node.getProperty("exo:eventName").getString();
            String eventDate = node.getProperty("exo:eventDate").getString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(eventDate));
            return new Event(eventName, calendar);

        } catch (RepositoryException e)
        {
            LOG.error("Error while loading event with name " + name, e);
        } catch (ParseException e)
        {
            LOG.error("Error while loading event with name and parsing date " + name, e);
        }
        return null;
    }

    @Override
    public List<Event> list()
    {
        List<Event> events = new ArrayList<Event>();
        try
        {
            NodeIterator nodeIterator = eventsNode.getNodes();
            while (nodeIterator.hasNext())
            {
                Node node = nodeIterator.nextNode();
                String eventName = node.getProperty("exo:eventName").getString();
                String eventDate = node.getProperty("exo:eventDate").getString();
                Calendar calendar = Calendar.getInstance();
                try
                {
                    calendar.setTime(dateFormat.parse(eventDate));
                } catch (ParseException e)
                {
                    LOG.error("Error while loading event with name and parsing date " + eventName, e);
                }
                events.add(new Event(eventName, calendar));
            }

        } catch (RepositoryException e)
        {
            LOG.error("Error while loading events ", e);
        }
        return events;
    }

    private void initSessionConfig()
    {
        try
        {
            ManageableRepository repository = repositoryService.getRepository("repository");
            Session session = repository.getSystemSession("collaboration");
            Node rootNode = session.getRootNode();
            if (rootNode.hasNode(EVENTS_ROOT_NODE_NAME))
            {
                eventsNode = rootNode.getNode(EVENTS_ROOT_NODE_NAME);
            }
            else
            {
                eventsNode = rootNode.addNode(EVENTS_ROOT_NODE_NAME);
            }
        } catch (RepositoryException e)
        {
            LOG.error("Error while initializing events home node", e);
        } catch (RepositoryConfigurationException e)
        {
            LOG.error("Error while initializing events home node", e);
        }
    }
}
