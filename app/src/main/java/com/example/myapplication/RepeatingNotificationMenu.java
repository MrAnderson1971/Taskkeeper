package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.RepeatingNotification;
import com.example.myapplication.Dialogs.ConfirmDeleteDialog;

import java.util.ArrayList;
import java.util.Collections;

public class RepeatingNotificationMenu extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    public static RepeatingNotification selectedNotification; // used when user wants to edit

    private Spinner repeatingNotificationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeating_notification_menu);
        setTitle(MainActivity.dict.get(Keys.REPEATING_NOTIFICATIONS));

        selectedNotification = null;

        Button backButton = findViewById(R.id.backButton);
        Button addNewButton = findViewById(R.id.placeholder);
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        addNewButton.setText(MainActivity.dict.get(Keys.ADD_NEW));
        editButton.setText(MainActivity.dict.get(Keys.EDIT));
        deleteButton.setText(MainActivity.dict.get(Keys.DELETE));

        repeatingNotificationSpinner = findViewById(R.id.repeatingNotificationSpinner);

        updateSpinner();

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RepeatingNotificationMenu.this, MoreOptionsMenu.class));
            }
        });

        addNewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RepeatingNotificationMenu.this, AddRepeatingNotification.class));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selected = repeatingNotificationSpinner.getSelectedItem();
                if (selected != null)
                {
                    selectedNotification = (RepeatingNotification) selected;
                    startActivity(new Intent(RepeatingNotificationMenu.this, AddRepeatingNotification.class));
                } else
                {
                    Toast.makeText(RepeatingNotificationMenu.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selected = repeatingNotificationSpinner.getSelectedItem();
                if (selected != null)
                {
                    selectedNotification = (RepeatingNotification) selected;
                    openDialog();
                } else
                {
                    Toast.makeText(RepeatingNotificationMenu.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openDialog()
    {
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
        dialog.show(getSupportFragmentManager(), "Delete");
    }

    private void updateSpinner()
    {
        ArrayAdapter<RepeatingNotification> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MainCalendar.allRepeatingNotifications);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatingNotificationSpinner.setAdapter(adapter);
    }

    @Override
    public void applyDeletion(boolean arg)
    {
        if (arg)
        {
            MainCalendar.allRepeatingNotifications.remove(selectedNotification);
            DataManager.save(RepeatingNotificationMenu.this, MainCalendar.allRepeatingNotifications, MainActivity.activeUser.getRepeatingNotificationSaveFileName());
            updateSpinner();
            selectedNotification = null;
        }
    }
}
