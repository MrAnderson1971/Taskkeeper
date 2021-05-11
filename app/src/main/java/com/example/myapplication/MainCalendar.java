package com.example.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Classes.DailyTask;
import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.DateAndTime;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.Classes.Reminder;
import com.example.myapplication.Classes.RepeatingNotification;
import com.example.myapplication.Dialogs.NotifyDialog;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class MainCalendar extends AppCompatActivity
{

    public static DateAndTime selectedDate;
    private static SimpleDateFormat dateFormatMonth;

    public static HashMap<Long, ArrayList<Reminder>> allReminders;
    public static ArrayList<RepeatingNotification> allRepeatingNotifications;
    public static ArrayList<DailyTask> allDailyTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calendar);
        setTitle(MainActivity.dict.get(Keys.CALENDAR));

        dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Languages.getLocale());
        Reminder.displayDateInString = false;

        allReminders = new HashMap<>();
        allDailyTasks = new ArrayList<>();
        allRepeatingNotifications = new ArrayList<>();

        try
        {
            if (DataManager.fileExists(this, MainActivity.activeUser.getReminderSaveFileName()))
            {
                allReminders = (HashMap<Long, ArrayList<Reminder>>) DataManager.load(this, MainActivity.activeUser.getReminderSaveFileName());
            }
        } catch (Exception ex)
        {
            allReminders = new HashMap<>();
        }
        if (allReminders == null)
        {
            allReminders = new HashMap<>();
        }

        try
        {
            if (DataManager.fileExists(this, MainActivity.activeUser.getRepeatingNotificationSaveFileName()))
            {
                MainCalendar.allRepeatingNotifications = (ArrayList<RepeatingNotification>) DataManager.load(this, MainActivity.activeUser.getRepeatingNotificationSaveFileName());
                Collections.sort(allRepeatingNotifications);
            }
        } catch (Exception ex)
        {
            allRepeatingNotifications = new ArrayList<>();
        }
        if (allRepeatingNotifications == null)
        {
            allRepeatingNotifications = new ArrayList<>();
        }

        try
        {
            if (DataManager.fileExists(this, MainActivity.activeUser.getDailyTaskSaveFileName()))
            {
                allDailyTasks = (ArrayList<DailyTask>) DataManager.load(this, MainActivity.activeUser.getDailyTaskSaveFileName());
                Collections.sort(allDailyTasks);
            }
        } catch (Exception ex)
        {
            allDailyTasks = new ArrayList<>();
        }
        if (allDailyTasks == null)
        {
            allDailyTasks = new ArrayList<>();
        }

        ArrayList<Long> empty = new ArrayList<>();

        /*
        Go through all user's notifications.
        If notification has passed, send message,
        then delete that notification.
         */
        for (long l : allReminders.keySet())
        {
            Collections.sort(allReminders.get(l));
            Iterator<Reminder> it = allReminders.get(l).iterator();
            while (it.hasNext())
            {
                Reminder next = it.next();
                if (next.hasPassed())
                {
                    if (next.getNotify())
                    {
                        openNotifyDialog(next.getName(), next.getDescription());
                    }
                    it.remove();
                }
            }

            if (allReminders.get(l).isEmpty())
            {
                empty.add(l);
            }
        }

        /*
        Deleting empty items this way because I don't
        feel comfortable doing it the other way.
         */
        for (long l : empty)
        {
            allReminders.remove(l);
        }

        DataManager.save(this, allReminders, MainActivity.activeUser.getReminderSaveFileName());

        for (RepeatingNotification r : allRepeatingNotifications)
        {
            //Toast.makeText(this, r.getDateAndTime().calendarToString(), Toast.LENGTH_SHORT).show();
            if (r.hasPassed())
            {
                r.setDateAndTime();

                if (r.getNotify())
                {
                    openNotifyDialog(r.getName(), r.getDescription(), MainActivity.dict.get(Keys.NEXT_NOTIFICATION_AT) + r.getDateAndTime().calendarToString());
                }
            }
        }
        DataManager.save(this, allRepeatingNotifications, MainActivity.activeUser.getRepeatingNotificationSaveFileName());

        /*
        If first time accessing after midnight, reset all
        daily tasks where resetEveryMidnight=true and show a notification.
         */
        //boolean midnightPassed = false;
        boolean showDailyTaskResetDialog = false;
        if (!allDailyTasks.isEmpty())
        {
            if (allDailyTasks.get(0).hasPassed())
            {
                for (DailyTask d : allDailyTasks)
                {
                    if (d.getNotify())
                    {
                        d.resetProgress();
                        d.setDateAndTime();
                        showDailyTaskResetDialog = true;
                    }
                }
                DataManager.save(this, allDailyTasks, MainActivity.activeUser.getDailyTaskSaveFileName());
            }
        }
        if (showDailyTaskResetDialog)
        {
            openNotifyDialog(MainActivity.dict.get(Keys.DAILY_TASKS), MainActivity.dict.get(Keys.DAILY_TASKS_HAVE_BEEN_RESET));
        }

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        Date today = Calendar.getInstance().getTime();
        actionBar.setTitle(dateFormatMonth.format(today));

        final CompactCalendarView compactCalendar = findViewById(R.id.calendarView);
        Button optionsButton = findViewById(R.id.optionsButton);
        Button backButton = findViewById(R.id.backButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button viewAllRemindersButton = findViewById(R.id.viewAllRemindersButton);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        viewButton.setText(MainActivity.dict.get(Keys.VIEW));
        optionsButton.setText(MainActivity.dict.get(Keys.MORE));
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        viewAllRemindersButton.setText(MainActivity.dict.get(Keys.VIEW_ALL_REMINDERS));

        for (long l : allReminders.keySet())
        {
            Event e = new Event(Color.BLUE, DateAndTime.reverseHash(l).getTime());
            compactCalendar.addEvent(e);
        }

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener()
        {
            @Override
            public void onDayClick(Date dateClicked)
            {
                Date clickedDate = dateClicked;
                Calendar cal = Calendar.getInstance();
                cal.setTime(clickedDate);
                selectedDate = new DateAndTime(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR), 0, 0);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth)
            {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainCalendar.this, MoreOptionsMenu.class)); // reload the spinner after deletion
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainCalendar.this, MainActivity.class));
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // Happens when the users presses the button without interacting with the calendar first
                if (selectedDate == null)
                {
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    selectedDate = new DateAndTime(day, month, year, 0, 0);
                }
                if (selectedDate.compareTo(DateAndTime.getToday()) < 0)
                {
                    Toast.makeText(MainCalendar.this, MainActivity.dict.get(Keys.DATE_HAS_ALREADY_PASSED), Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(MainCalendar.this, AddNewNotificationMenu.class));
            }
        });

        viewAllRemindersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainCalendar.this, RemindersSummary.class));
            }
        });
    }

    private void openNotifyDialog(String title, String text)
    {
        openNotifyDialog(title, text, "");
    }

    private void openNotifyDialog(String title, String text, String next)
    {
        NotifyDialog dialog = new NotifyDialog();
        dialog.setNotification(title, text, next);
        dialog.show(getSupportFragmentManager(), "Notification");
    }

    /*private boolean checkDate()
    {
        DateAndTime today = DateAndTime.getNow();
        today.setHour(0);
        today.setMinute(0);

        if (!DataManager.fileExists(this, MainActivity.LAST_ACCESSSED_FILE_PATH))
        {
            DataManager.save(this, today, MainActivity.LAST_ACCESSSED_FILE_PATH);
            return false;
        }

        boolean v = !today.equals(DataManager.load(this, MainActivity.LAST_ACCESSSED_FILE_PATH));
        DataManager.save(this, today, MainActivity.LAST_ACCESSSED_FILE_PATH);
        return v;
    }*/
}
