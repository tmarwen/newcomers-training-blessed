package org.exoplatform.support.jcr.services;

import org.exoplatform.support.model.beans.Event;

import java.util.Calendar;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/11/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public interface EventService
{
    void create(String name, String date);
    boolean delete(String name);
    void update(String name, String newName, Calendar newDate);
    Event get(String name);
    List<Event> list();
}
