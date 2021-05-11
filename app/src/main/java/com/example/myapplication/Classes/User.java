package com.example.myapplication.Classes;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class User implements Serializable, Comparable<User>
{
    private String name;
    private ArrayList<Reminder> reminders = new ArrayList<>();
    private long lastAccessed;

    public User(String name)
    {
        this.name = name;
        this.lastAccessed = System.currentTimeMillis();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void updateLastAccessed()
    {
        lastAccessed = System.currentTimeMillis();
    }

    public static User getUserByName(ArrayList<User> users, String name)
    {
        for (User u : users)
        {
            if (u.name.equals(name))
            {
                return u;
            }
        }

        throw new NoSuchElementException("No user with " + name + " found.");
    }

    public void addReminder(Reminder reminder)
    {
        reminders.add(reminder);
    }

    /**
     * Removes specified reminder
     * @param reminder
     * @return true if the operation is successful
     */
    public boolean removeReminder(Reminder reminder)
    {
        if (reminders.contains(reminder))
        {
            reminders.remove(reminder);
            return true;
        }
        return false;
    }

    public void saveReminders(Context context)
    {
        DataManager.save(context, reminders, getReminderSaveFileName());
    }

    public ArrayList<Reminder> loadReminders(Context context)
    {
        return (ArrayList<Reminder>) DataManager.load(context, getReminderSaveFileName());
    }

    @Override
    public String toString()
    {
        return name;
    }

    public String getReminderSaveFileName()
    {
        return toString() + ".txt";
    }

    public String getRepeatingNotificationSaveFileName()
    {
        return "repeating" + getReminderSaveFileName();
    }

    public String getDailyTaskSaveFileName()
    {
        return "task" + getReminderSaveFileName();
    }

    /**
     * Sort users by last accessed.
     * @param other
     * @return
     */
    @Override
    public int compareTo(User other)
    {
        return (int) (other.lastAccessed - this.lastAccessed);
    }
}
