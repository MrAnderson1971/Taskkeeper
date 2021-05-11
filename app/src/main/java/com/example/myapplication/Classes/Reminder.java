package com.example.myapplication.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Reminder implements Serializable, Comparable<Reminder>
{

    protected DateAndTime dateAndTime;
    protected String name;
    protected String description;
    protected boolean notify;

    public static boolean displayDateInString;

    public Reminder(DateAndTime dateAndTime, String name, String desc, boolean notify)
    {
        this.dateAndTime = dateAndTime;
        this.name = name;
        this.description = desc;
        this.notify = notify;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean getNotify()
    {
        return notify;
    }

    public void setNotify(boolean notify)
    {
        this.notify = notify;
    }

    public DateAndTime getDateAndTime()
    {
        return dateAndTime;
    }

    public void setDateAndTime(DateAndTime dateAndTime)
    {
        this.dateAndTime = dateAndTime;
    }

    @Override
    public int compareTo(Reminder other)
    {
        return this.dateAndTime.compareTo(other.dateAndTime);
    }

    public String toString()
    {
        if (displayDateInString)
        {
            return dateAndTime.calendarToString() + "    " + name;
        }
        return dateAndTime.toString() + "    " + name;
    }

    public static Reminder getItemWithName(ArrayList<? extends Reminder> list, String name, Reminder current)
    {
        for (Object o : list)
        {
            Reminder r = (Reminder) o;
            if (r.getName().equals(name) && r != current)
            {
                return r;
            }
        }
        return null;
    }

    public boolean hasPassed()
    {
        return this.dateAndTime.compareTo(DateAndTime.getNow()) < 0;
    }

}
