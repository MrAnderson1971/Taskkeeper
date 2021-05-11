package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Reminder;
import com.example.myapplication.Classes.RepeatingNotification;
import com.example.myapplication.Classes.Time;
import com.example.myapplication.Dialogs.TimePickerDialog;
import com.example.myapplication.Dialogs.WeekSelectionDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class AddRepeatingNotification extends AppCompatActivity
        implements TimePickerDialog.TimePickerDialogListener, WeekSelectionDialog.WeekSelectionDialogListener
{

    private ArrayList<Time> selectedTimes;
    private ArrayList<Integer> selectedDays;

    private Spinner timeDeletionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repeating_notification);
        setTitle(MainActivity.dict.get((RepeatingNotificationMenu.selectedNotification == null) ? Keys.ADD_NEW_TITLE : Keys.EDIT));

        // default to all days checked
        selectedDays = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        selectedTimes = new ArrayList<>();

        final Button timeSelectionButton = findViewById(R.id.timeSelectionButton);
        Button dayOfWeekButton = findViewById(R.id.dayOfWeekButton);
        Button removeTimeButton = findViewById(R.id.removeTimeButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button confirmButton = findViewById(R.id.confirmButton);
        final EditText notificationNameField = findViewById(R.id.notificationNameField);
        final EditText descriptionField = findViewById(R.id.descriptionField);
        final CheckBox sendNotificationCheckbox = findViewById(R.id.sendNotificationCheckbox);
        TextView repeatNotificationEveryText = findViewById(R.id.repeatNotificationEveryText);
        TextView dayAtText = findViewById(R.id.dayAtText);
        TextView onText = findViewById(R.id.onText);

        onText.setText(MainActivity.dict.get(Keys.ON));
        dayAtText.setText(MainActivity.dict.get(Keys.DAY_AT));
        notificationNameField.setHint(MainActivity.dict.get(Keys.NAME));
        descriptionField.setHint(MainActivity.dict.get(Keys.DESCRIPTION));
        dayOfWeekButton.setText(MainActivity.dict.get(Keys.DAYS_OF_WEEK));
        timeSelectionButton.setText(MainActivity.dict.get(Keys.SELECT_TIME));
        removeTimeButton.setText(MainActivity.dict.get(Keys.DELETE_SELECTED_TIME));
        sendNotificationCheckbox.setText(MainActivity.dict.get(Keys.SEND_NOTIFICATION));
        repeatNotificationEveryText.setText(MainActivity.dict.get(Keys.REPEAT_NOTIFICATION_EVERY));
        cancelButton.setText(MainActivity.dict.get(Keys.CANCEL));
        confirmButton.setText(MainActivity.dict.get(Keys.CONFIRM));

        timeDeletionSpinner = findViewById(R.id.timeDeletionSpinner);

        if (RepeatingNotificationMenu.selectedNotification != null)
        {
            notificationNameField.setText(RepeatingNotificationMenu.selectedNotification.getName());
            descriptionField.setText(RepeatingNotificationMenu.selectedNotification.getDescription());
            sendNotificationCheckbox.setChecked(RepeatingNotificationMenu.selectedNotification.getNotify());

            selectedDays = RepeatingNotificationMenu.selectedNotification.getNotificationDays();
            selectedTimes = RepeatingNotificationMenu.selectedNotification.getNotificationTimes();
            updateSpinner();
        }

        timeSelectionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openTimeDialog();
            }
        });

        removeTimeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selectedTime = timeDeletionSpinner.getSelectedItem();
                if (selectedTime != null)
                {
                    selectedTimes.remove(selectedTime);
                    updateSpinner();
                } else
                {
                    Toast.makeText(AddRepeatingNotification.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dayOfWeekButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDayOfWeekDialog();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddRepeatingNotification.this, RepeatingNotificationMenu.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = notificationNameField.getText().toString();

                if (name.isEmpty() || selectedDays.isEmpty())
                {
                    Toast.makeText(AddRepeatingNotification.this, MainActivity.dict.get(Keys.PLEASE_FILL_IN_THE_BLANKS), Toast.LENGTH_SHORT).show();
                } else if (Reminder.getItemWithName(MainCalendar.allRepeatingNotifications, name, RepeatingNotificationMenu.selectedNotification) != null)
                {
                    Toast.makeText(AddRepeatingNotification.this, MainActivity.dict.get(Keys.NAME_TAKEN), Toast.LENGTH_SHORT).show();
                } else
                {

                    if (selectedTimes.isEmpty())
                    {
                        selectedTimes.add(new Time(0, 0));
                    }

                    String desc = descriptionField.getText().toString();
                    boolean notify = sendNotificationCheckbox.isChecked();

                    if (RepeatingNotificationMenu.selectedNotification != null)
                    {
                        MainCalendar.allRepeatingNotifications.remove(RepeatingNotificationMenu.selectedNotification);
                    }

                    RepeatingNotification newRepeating = new RepeatingNotification(selectedDays, selectedTimes, name, desc, notify);
                    newRepeating.setDateAndTime();
                    MainCalendar.allRepeatingNotifications.add(newRepeating);

                    DataManager.save(AddRepeatingNotification.this, MainCalendar.allRepeatingNotifications, MainActivity.activeUser.getRepeatingNotificationSaveFileName());
                    Toast.makeText(AddRepeatingNotification.this, MainActivity.dict.get(Keys.NEXT_NOTIFICATION_AT) + newRepeating.getDateAndTime().calendarToString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddRepeatingNotification.this, RepeatingNotificationMenu.class));
                }
            }
        });
    }

    private void openTimeDialog()
    {
        TimePickerDialog dialog = new TimePickerDialog();
        dialog.show(getSupportFragmentManager(), "Time Picker");
    }

    private void openDayOfWeekDialog()
    {
        WeekSelectionDialog dialog = new WeekSelectionDialog();
        dialog.setCheckBoxes(selectedDays);
        dialog.show(getSupportFragmentManager(), "Day of Week");
    }

    @Override
    public void applyTime(int hour, int minute)
    {
        selectedTimes.add(new Time(hour, minute));
        updateSpinner();
    }

    private void updateSpinner()
    {
        ArrayAdapter<Time> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selectedTimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeDeletionSpinner.setAdapter(adapter);
    }

    @Override
    public void applyWeekSelection(ArrayList<Integer> sd)
    {
        selectedDays = sd;
    }
}