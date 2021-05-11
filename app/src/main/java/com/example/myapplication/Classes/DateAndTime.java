package com.example.myapplication.Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTime implements Comparable<DateAndTime>, Serializable
{
    private int year;
    private int month;
    private int day;

    int hour;
    int minute;

    public DateAndTime(int day, int month, int year, int hour, int minute)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    // Can't use hashCode(), because I need long, not int
    public long hash()
    {
        // yyyy mm dd hh mm
        return minute + 100 * hour + 10000 * day + 1000000 * month + (long) Math.pow(10, 8) * year;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof DateAndTime))
        {
            return false;
        }
        return this.hash() == ((DateAndTime) other).hash();
    }

    @Override
    public int compareTo(DateAndTime other)
    {
        return (int) (this.hash() - other.hash());
    }

    @Override
    public String toString()
    {
        if (minute >= 10)
        {
            return hour + ":" + minute;
        }
        return hour + ":0" + minute;
    }

    public String formatDate(SimpleDateFormat dateFormat)
    {
        Date temp = new Date(year - 1900, month - 1, day);
        return dateFormat.format(temp);
    }

    public static Date reverseHash(long hash)
    {
        int day = (int) (hash % 100);
        int month = (int) ((hash / 100) % 100);
        int year = (int) (hash / 10000);

        return new Date(year - 1900, month - 1, day);
    }

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public long hashDateWithoutTime()
    {
        // yyyy mm dd
        return day + 100 * month + 10000 * year;

    }

    /**
     *
     * @return current date and time.
     */
    public static DateAndTime getNow()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        return new DateAndTime(day, month, year, hour, minute);
    }

    /**
     *
     * @return current date.
     */
    public static DateAndTime getToday()
    {
        DateAndTime today = getNow();
        today.hour = 0;
        today.minute = 0;
        return today;
    }

    public String calendarToString()
    {
        return "" + year + "/" + month + "/" + day + " at " + toString();
    }
}
