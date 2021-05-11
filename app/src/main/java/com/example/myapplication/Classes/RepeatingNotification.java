package com.example.myapplication.Classes;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class RepeatingNotification extends Reminder
{

    private ArrayList<Integer> notificationDays;
    private ArrayList<Time> notificationTimes;

    public RepeatingNotification(ArrayList<Integer> notificationDays, ArrayList<Time> notificationTimes, String name, String desc, boolean notify)
    {
        super(null, name, desc, notify);
        this.notificationDays = notificationDays;
        this.notificationTimes = notificationTimes;
    }

    public void setDateAndTime()
    {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Collections.sort(notificationTimes);

        if (notificationDays.contains(dayOfWeek)) // this day of week is included within notification
        {
            boolean timeFound = false;

            for (Time t: notificationTimes)
            {
                if (t.compareTo(Time.getCurrentTime()) > 0) // there is a time that hasn't passed yet
                {
                    calendar.set(Calendar.HOUR_OF_DAY, t.getHour());
                    calendar.set(Calendar.MINUTE, t.getMinute());
                    timeFound = true;
                    break;
                }
            }

            if (!timeFound)
            {
                for (int i = 0; i < 7; i++) // find next day this notification will fall on.
                {
                    if (notificationDays.contains((i + dayOfWeek) % 7 + 1))
                    {
                        calendar.add(Calendar.DATE, i + 1);
                        calendar.set(Calendar.HOUR_OF_DAY, notificationTimes.get(0).getHour());
                        calendar.set(Calendar.MINUTE, notificationTimes.get(0).getMinute());
                        timeFound = true;
                        break;
                    }
                }
            }

            // still not found?
            if (!timeFound) // fast forward one week, then add earliest time
            {
                calendar.add(Calendar.DATE, 7);
                calendar.set(Calendar.HOUR_OF_DAY, notificationTimes.get(0).getHour());
                calendar.set(Calendar.MINUTE, notificationTimes.get(0).getMinute());
            }
        } else
        {
            for (int i = 0; i < 7; i++) // find next day of week this notification will fall on.
            {
                if (notificationDays.contains((i + dayOfWeek) % 7 + 1))
                {
                    calendar.add(Calendar.DATE, i + 1);
                    calendar.set(Calendar.HOUR_OF_DAY, notificationTimes.get(0).getHour());
                    calendar.set(Calendar.MINUTE, notificationTimes.get(0).getMinute());
                    break;
                }
            }
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        this.dateAndTime = new DateAndTime(day, month + 1, year, hour, minute);
    }

    public int compareTo(Reminder other)
    {
        if (!(other instanceof RepeatingNotification))
        {
            throw new IllegalArgumentException("Cannot compare Reminder with RepeatingNotification.");
        }
        return this.name.compareTo(other.name);
    }

    public ArrayList<Integer> getNotificationDays()
    {
        return notificationDays;
    }

    public ArrayList<Time> getNotificationTimes()
    {
        return notificationTimes;
    }

    public String toString()
    {
        return name;
    }

    /**
     * Use to detect name conflicts
     *
     * @param list
     * @param name
     * @param current Pass selected object so that object's name won't conflict with itself
     *                when renaming. Can be null.
     * @return null if no name conflict found
     */
    public static RepeatingNotification getRepeatingNotificationWithName(ArrayList<RepeatingNotification> list, String name, RepeatingNotification current)
    {
        for (RepeatingNotification r : list)
        {
            if (r.getName().equals(name) && r != current)
            {
                return r;
            }
        }
        return null;
    }
}
