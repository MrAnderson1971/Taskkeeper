package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;

public class ViewDailyTask extends AppCompatActivity
{

    private TextView completionText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_task);
        setTitle(DailyTasksMenu.selectedTask.getName());

        Button backButton = findViewById(R.id.backButton);
        Button plusButton = findViewById(R.id.plusButton);
        Button editButton = findViewById(R.id.editButton);
        final Button minusButton = findViewById(R.id.minusButton);
        TextView timesCompletedText = findViewById(R.id.timesCompletedText);

        TextView descriptionText = findViewById(R.id.descriptionText);
        completionText = findViewById(R.id.completionText);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        editButton.setText(MainActivity.dict.get(Keys.EDIT));
        timesCompletedText.setText(MainActivity.dict.get(Keys.TIMES_COMPLETED));

        descriptionText.setText(DailyTasksMenu.selectedTask.getDescription());
        updateCompletionText();

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ViewDailyTask.this, DailyTasksMenu.class));
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DailyTasksMenu.selectedTask.increment();
                updateCompletionText();
                DataManager.save(ViewDailyTask.this, MainCalendar.allDailyTasks, MainActivity.activeUser.getDailyTaskSaveFileName());
                if (DailyTasksMenu.selectedTask.getProgress() > 0) // renable it
                {
                    minusButton.setEnabled(true);
                }
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (DailyTasksMenu.selectedTask.getProgress() <= 0) // disable button if number will go negative
                {
                    minusButton.setEnabled(false);
                } else
                {
                    DailyTasksMenu.selectedTask.decrement();
                    updateCompletionText();
                    DataManager.save(ViewDailyTask.this, MainCalendar.allDailyTasks, MainActivity.activeUser.getDailyTaskSaveFileName());
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ViewDailyTask.this, AddDailyTask.class));
            }
        });
    }

    private void updateCompletionText()
    {
        completionText.setText(DailyTasksMenu.selectedTask.getProgress() + "/" + DailyTasksMenu.selectedTask.getGoal());
        completionText.setTextColor(Color.parseColor((DailyTasksMenu.selectedTask.isCompleted() ? DailyTasksMenu.COMPLETED_COLOR : DailyTasksMenu.INCOMPLETE_COLOR)));
    }
}
