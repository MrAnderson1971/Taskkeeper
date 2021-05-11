package com.example.myapplication;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.DateAndTime;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Reminder;
import com.example.myapplication.Dialogs.TimePickerDialog;

public class AddNewNotification extends AppCompatActivity implements TimePickerDialog.TimePickerDialogListener
{

    private static int hour;
    private static int minute;

    /**
     * Where the program will go after the user clicks Confirm/Back.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_notification);
        setTitle(MainActivity.dict.get((AddNewNotificationMenu.selectedReminder == null) ? Keys.ADD_NEW : Keys.EDIT));

        hour = 0;
        minute = 0;

        Button cancelButton = findViewById(R.id.cancelButton);
        Button confirmButton = findViewById(R.id.confirmButton);
        Button openTimePickerDialog = findViewById(R.id.openTimePickerDialog);

        final EditText notificationNameField = findViewById(R.id.notificationNameField);
        final EditText descriptionField = findViewById(R.id.descriptionField);
        final CheckBox sendNotificationCheckbox = findViewById(R.id.sendNotificationCheckbox);

        notificationNameField.setHint(MainActivity.dict.get(Keys.NAME));
        descriptionField.setHint(MainActivity.dict.get(Keys.DESCRIPTION));
        confirmButton.setText(MainActivity.dict.get(Keys.CONFIRM));
        cancelButton.setText(MainActivity.dict.get(Keys.CANCEL));
        sendNotificationCheckbox.setText(MainActivity.dict.get(Keys.SEND_NOTIFICATION));
        openTimePickerDialog.setText(MainActivity.dict.get(Keys.SELECT_TIME));

        if (AddNewNotificationMenu.selectedReminder != null)
        {
            notificationNameField.setText(AddNewNotificationMenu.selectedReminder.getName());
            descriptionField.setText(AddNewNotificationMenu.selectedReminder.getDescription());
        }

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddNewNotification.this, AddNewNotificationMenu.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                String name = notificationNameField.getText().toString();
                String desc = descriptionField.getText().toString();

                if (name.isEmpty())
                {
                    Toast.makeText(AddNewNotification.this, MainActivity.dict.get(Keys.PLEASE_FILL_IN_THE_BLANKS), Toast.LENGTH_SHORT).show();
                } else if (Reminder.getItemWithName(AddNewNotificationMenu.todaysReminders, name, AddNewNotificationMenu.selectedReminder) != null)
                {
                    Toast.makeText(AddNewNotification.this, MainActivity.dict.get(Keys.NAME_TAKEN), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean notifyEnabled = sendNotificationCheckbox.isChecked();
                    DateAndTime notificationTime = new DateAndTime(MainCalendar.selectedDate.getDay(), MainCalendar.selectedDate.getMonth(), MainCalendar.selectedDate.getYear(), hour, minute);

                    Reminder newReminder = new Reminder(notificationTime, name, desc, notifyEnabled);

                    if (!newReminder.hasPassed())
                    {

                        if (AddNewNotificationMenu.selectedReminder == null)
                        {
                            AddNewNotificationMenu.todaysReminders.add(newReminder);
                            MainCalendar.allReminders.put(notificationTime.hashDateWithoutTime(), AddNewNotificationMenu.todaysReminders);
                        } else
                        {
                            AddNewNotificationMenu.selectedReminder.setName(name);
                            AddNewNotificationMenu.selectedReminder.setDescription(desc);
                            AddNewNotificationMenu.selectedReminder.setDateAndTime(notificationTime);

                        }

                        DataManager.save(AddNewNotification.this, MainCalendar.allReminders, MainActivity.activeUser.getReminderSaveFileName());
                        startActivity(new Intent(AddNewNotification.this, AddNewNotificationMenu.class));
                    } else
                    {
                        Toast.makeText(AddNewNotification.this, "Can't select time that's already passed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        openTimePickerDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openTimeDialog();
            }
        });
    }

    private void openTimeDialog()
    {
        TimePickerDialog dialog = new TimePickerDialog();
        if (AddNewNotificationMenu.selectedReminder != null)
        {
            dialog.setTime(AddNewNotificationMenu.selectedReminder.getDateAndTime().getHour(), AddNewNotificationMenu.selectedReminder.getDateAndTime().getMinute());
        } else
        {
            dialog.setTime(0, 0);
        }
        dialog.show(getSupportFragmentManager(), "Time Picker");
    }

    @Override
    public void applyTime(int h, int m)
    {
        hour = h;
        minute = m;
    }
}
