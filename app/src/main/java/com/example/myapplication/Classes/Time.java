package com.example.myapplication.Classes;

public class Time extends DateAndTime
{

    public Time(int hour, int minute)
    {
        super(0, 0, 0, hour, minute);
        this.hour = hour;
        this.minute = minute;
    }

    public static Time getCurrentTime()
    {
        DateAndTime today = DateAndTime.getNow();
        today.setYear(0);
        today.setMonth(0);
        today.setDay(0);

        return new Time(today.hour, today.minute);
    }
}
