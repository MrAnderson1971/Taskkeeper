package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.DailyTask;
import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Dialogs.ConfirmDeleteDialog;

import java.util.ArrayList;
import java.util.Collections;

public class DailyTasksMenu extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    private Spinner dailyTasksSpinner;

    public static final String COMPLETED_COLOR = "#AA008577";
    public static final String INCOMPLETE_COLOR = "#AA850028";

    public static DailyTask selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tasks_menu);
        setTitle(MainActivity.dict.get(Keys.DAILY_TASKS));

        selectedTask = null;

        Button backButton = findViewById(R.id.backButton);
        Button addNewButton = findViewById(R.id.addNewButton);
        Button viewSelectedButton = findViewById(R.id.viewSelectedButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        addNewButton.setText(MainActivity.dict.get(Keys.ADD_NEW));
        viewSelectedButton.setText(MainActivity.dict.get(Keys.VIEW));
        deleteButton.setText(MainActivity.dict.get(Keys.DELETE));

        dailyTasksSpinner = findViewById(R.id.dailyTasksSpinner);

        updateSpinner();

        addNewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(DailyTasksMenu.this, AddDailyTask.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(DailyTasksMenu.this, MoreOptionsMenu.class));
            }
        });

        viewSelectedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selection = dailyTasksSpinner.getSelectedItem();
                if (selection == null)
                {
                    Toast.makeText(DailyTasksMenu.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                } else
                {
                    selectedTask = (DailyTask) selection;
                    startActivity(new Intent(DailyTasksMenu.this, ViewDailyTask.class));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selection = dailyTasksSpinner.getSelectedItem();
                if (selection == null)
                {
                    Toast.makeText(DailyTasksMenu.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                } else
                {
                    selectedTask = (DailyTask) selection;
                    openDialog();
                }
            }
        });
    }

    private void updateSpinner()
    {
        ArrayAdapter<DailyTask> adapter = new ArrayAdapter<DailyTask>(this, android.R.layout.simple_spinner_item, MainCalendar.allDailyTasks)
        {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                textView.setBackgroundColor(Color.parseColor(MainCalendar.allDailyTasks.get(position).isCompleted() ? COMPLETED_COLOR : INCOMPLETE_COLOR));

                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dailyTasksSpinner.setAdapter(adapter);
    }

    @Override
    public void applyDeletion(boolean arg)
    {
        if (arg)
        {
            MainCalendar.allDailyTasks.remove(selectedTask);
            DataManager.save(DailyTasksMenu.this, MainCalendar.allDailyTasks, MainActivity.activeUser.getDailyTaskSaveFileName());
            updateSpinner();
            selectedTask = null;
        }
    }

    public void openDialog()
    {
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
        dialog.show(getSupportFragmentManager(), "Delete");
    }
}
//https://android--code.blogspot.com/2015/08/android-spinner-alternate-item-color.html