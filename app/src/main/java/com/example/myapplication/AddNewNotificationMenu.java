package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Language;
import com.example.myapplication.Classes.Reminder;
import com.example.myapplication.Dialogs.ConfirmDeleteDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class AddNewNotificationMenu extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    public static ArrayList<Reminder> todaysReminders;
    private static SimpleDateFormat dateFormat = (MainActivity.lang == Language.ENGLISH) ?
            new SimpleDateFormat("dd/MM/yyyy") : new SimpleDateFormat("yyyy/MM/dd"); // dates formatted differently in various languages

    public static Reminder selectedReminder; // used when user wants to edit

    private Spinner notificationsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_selection);
        setTitle(MainActivity.dict.get(Keys.ADD_NEW));

        selectedReminder = null;

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(MainCalendar.selectedDate.formatDate(dateFormat));

        todaysReminders = new ArrayList<>();
        if (MainCalendar.allReminders.containsKey(MainCalendar.selectedDate.hashDateWithoutTime()))
        {
            todaysReminders = MainCalendar.allReminders.get(MainCalendar.selectedDate.hashDateWithoutTime());
            Collections.sort(todaysReminders);
        }

        ArrayList<String> reminderNames = new ArrayList<>();
        for (Reminder r : todaysReminders)
        {
            reminderNames.add(r.toString());
        }

        Button newNotificationButton = findViewById(R.id.newNotificationButton);
        Button backButton = findViewById(R.id.backButton2);
        Button editNotificationButton = findViewById(R.id.editNotificationButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        notificationsSpinner = findViewById(R.id.notificationsSpinner);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        newNotificationButton.setText(MainActivity.dict.get(Keys.ADD_NEW));
        editNotificationButton.setText(MainActivity.dict.get(Keys.EDIT));
        deleteButton.setText(MainActivity.dict.get(Keys.DELETE));

        newNotificationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddNewNotificationMenu.this, AddNewNotification.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddNewNotificationMenu.this, MainCalendar.class));
            }
        });

        editNotificationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selection = notificationsSpinner.getSelectedItem();
                if (selection == null)
                {
                    Toast.makeText(AddNewNotificationMenu.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                } else
                {
                    selectedReminder = (Reminder) selection;
                    startActivity(new Intent(AddNewNotificationMenu.this, AddNewNotification.class));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selection = notificationsSpinner.getSelectedItem();
                if (selection == null)
                {
                    Toast.makeText(AddNewNotificationMenu.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                } else
                {
                    selectedReminder = (Reminder) selection;
                    openDeletionDialog();
                }
            }
        });

        updateSpinner();
    }

    private void openDeletionDialog()
    {
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
        dialog.show(getSupportFragmentManager(), "Delete");
    }

    private void updateSpinner()
    {
        ArrayAdapter<Reminder> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, todaysReminders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notificationsSpinner.setAdapter(adapter);
    }

    @Override
    public void applyDeletion(boolean arg)
    {
        if (arg)
        {
            todaysReminders.remove(selectedReminder);
            DataManager.save(this, MainCalendar.allReminders, MainActivity.activeUser.getReminderSaveFileName());
            updateSpinner();
            AddNewNotificationMenu.selectedReminder = null;
        }
    }
}
//https://stackoverflow.com/questions/41666566/constraintlayout-how-to-add-several-views-programmatically
