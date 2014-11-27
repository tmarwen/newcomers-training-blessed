package org.exoplatform.support.model.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

/**
 * Created by eXo Platform MEA on 26/11/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
@XmlRootElement(name = "Event")
public class Event
{
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "date")
    private Calendar date;

    public Event()
    {
    }

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

    @Override
    public String toString()
    {
        return "Event{" +
                "name:'" + name + '\'' +
                ", date:" + date +
                '}';
    }

    public int hashCode()
    {
        return (int) date.getTimeInMillis();
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof Event))
            return false;
        if (obj == this)
            return true;
        Event task = (Event) obj;
        return task.getName().equals(this.getName());
    }
}
