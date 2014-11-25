package org.exoplatform.support.beans;

import java.util.Calendar;

/**
 * Created by eXo Platform MEA on 25/11/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class Event
{
    private String name;
    private Calendar date;

    public Event(String name, Calendar date)
    {
        this.name = name;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Calendar getDate()
    {
        return date;
    }

    public void setDate(Calendar date)
    {
        this.date = date;
    }
}
