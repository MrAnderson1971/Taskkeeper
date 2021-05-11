package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Classes.DailyTask;
import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Reminder;

public class AddDailyTask extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_task);
        setTitle(MainActivity.dict.get((DailyTasksMenu.selectedTask == null) ? Keys.ADD_NEW_TITLE : Keys.EDIT));

        Button cancelButton = findViewById(R.id.cancelButton);
        Button confirmButton = findViewById(R.id.confirmButton);

        final EditText nameField = findViewById(R.id.nameField);
        final EditText descriptionField = findViewById(R.id.descriptionField);
        final EditText numberField = findViewById(R.id.numberField);
        //final CheckBox notifyCheckbox = findViewById(R.id.notifyCheckbox);
        final CheckBox resetEveryMidnightCheckbox = findViewById(R.id.resetEveryMidnightCheckbox);
        TextView howManyTimesText = findViewById(R.id.howManyTimesText);

        nameField.setHint(MainActivity.dict.get(Keys.NAME));
        descriptionField.setHint(MainActivity.dict.get(Keys.DESCRIPTION));
        howManyTimesText.setText(MainActivity.dict.get(Keys.HOW_MANY_TIMES));
        //notifyCheckbox.setText(MainActivity.dict.get(Keys.SEND_NOTIFICATION));
        resetEveryMidnightCheckbox.setText(MainActivity.dict.get(Keys.RESET_EVERY_MIDNIGHT));
        cancelButton.setText(MainActivity.dict.get(Keys.CANCEL));
        confirmButton.setText(MainActivity.dict.get(Keys.CONFIRM));

        if (DailyTasksMenu.selectedTask != null)
        {
            nameField.setText(DailyTasksMenu.selectedTask.getName());
            descriptionField.setText(DailyTasksMenu.selectedTask.getDescription());
            numberField.setText("" + DailyTasksMenu.selectedTask.getGoal());
            resetEveryMidnightCheckbox.setChecked(DailyTasksMenu.selectedTask.getNotify());
        }

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddDailyTask.this, DailyTasksMenu.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = nameField.getText().toString();
                String n = numberField.getText().toString();

                if (name.isEmpty() || n.isEmpty())
                {
                    Toast.makeText(AddDailyTask.this, MainActivity.dict.get(Keys.PLEASE_FILL_IN_THE_BLANKS), Toast.LENGTH_SHORT).show();
                } else if (Reminder.getItemWithName(MainCalendar.allDailyTasks, name, DailyTasksMenu.selectedTask) != null)
                {
                    Toast.makeText(AddDailyTask.this, MainActivity.dict.get(Keys.NAME_TAKEN), Toast.LENGTH_LONG).show();
                } else
                {
                    String description = descriptionField.getText().toString();
                    int goal = Integer.parseInt(n);
                    boolean resetEveryMidnight = resetEveryMidnightCheckbox.isChecked();
                    //boolean sendNotification = notifyCheckbox.isChecked();
                    //boolean sendNotification = true;

                    if (DailyTasksMenu.selectedTask == null)
                    {
                        DailyTask newDailyTask = new DailyTask(goal, name, description, resetEveryMidnight);
                        newDailyTask.setDateAndTime();
                        MainCalendar.allDailyTasks.add(newDailyTask);
                    } else
                    {
                        DailyTasksMenu.selectedTask.setName(name);
                        DailyTasksMenu.selectedTask.setDescription(description);
                        DailyTasksMenu.selectedTask.setGoal(goal);
                        DailyTasksMenu.selectedTask.setNotify(resetEveryMidnight);
                        DailyTasksMenu.selectedTask.setDateAndTime();
                    }

                    DataManager.save(AddDailyTask.this, MainCalendar.allDailyTasks, MainActivity.activeUser.getDailyTaskSaveFileName());
                    Toast.makeText(AddDailyTask.this, MainActivity.dict.get(Keys.SUCCESSFUL), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddDailyTask.this, DailyTasksMenu.class));
                }
            }
        });
    }
}
