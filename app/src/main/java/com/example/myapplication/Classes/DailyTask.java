package com.example.myapplication.Classes;

import java.util.ArrayList;
import java.util.Arrays;

public class DailyTask extends RepeatingNotification
{

    private int goal;
    private int progress;

    public DailyTask(int goal, String name, String desc, boolean resetEveryMidnight)
    {
        super(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)),
                new ArrayList<>(Arrays.asList(new Time(0, 0))), // every day at midnight
                name, desc, resetEveryMidnight);

        this.goal = goal;
        this.progress = 0;
    }

    public int getProgress()
    {
        return progress;
    }

    public void increment()
    {
        progress++;
    }

    public void decrement()
    {
        if (progress > 0)
        {
            progress--;
        }
    }

    public void resetProgress()
    {
        progress = 0;
    }

    public int getGoal()
    {
        return goal;
    }

    public void setGoal(int goal)
    {
        this.goal = goal;
    }

    public String toString()
    {
        return progress + "/" + goal + ": " + name;
    }

    public boolean isCompleted()
    {
        return goal <= progress;
    }

    public int compareTo(Reminder o)
    {
        if (!(o instanceof DailyTask))
        {
            throw new IllegalArgumentException("Not instance of DailyTask.");
        }

        DailyTask other = (DailyTask) o;

        if (other.isCompleted() == this.isCompleted()) // put incomplete tasks on top
        {
            return this.name.compareTo(other.name);
        }
        return this.isCompleted() ? 1 : -1;
    }

    /**
     * Use to detect name conflicts
     * @param list
     * @param name
     * @param current Pass selected object so that object's name won't conflict with itself
     *                when renaming.
     * @return null if no name conflict found
     */
    public static DailyTask getDailyTaskWithName(ArrayList<DailyTask> list, String name, DailyTask current)
    {
        for (DailyTask d : list)
        {
            if (d.getName().equals(name) && d != current)
            {
                return d;
            }
        }
        return null;
    }

    /**
     * Resets all tasks back to 0.
     * Call once per day.
     * @param list
     */
    public static void resetAll(ArrayList<DailyTask> list)
    {
        for (DailyTask d : list)
        {
            d.progress = 0;
        }
    }
}
