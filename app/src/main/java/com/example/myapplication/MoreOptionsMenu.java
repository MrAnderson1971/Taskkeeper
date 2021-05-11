package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Classes.Keys;

public class MoreOptionsMenu extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options_menu);
        setTitle(MainActivity.dict.get(Keys.MENU));

        Button addRepeatingNotificationButton = findViewById(R.id.addRepeatingNotificationButton);
        Button backButton = findViewById(R.id.backButton);
        Button addDailyTasksButton = findViewById(R.id.dailyTasksButton);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        addRepeatingNotificationButton.setText(MainActivity.dict.get(Keys.REPEATING_NOTIFICATIONS));
        addDailyTasksButton.setText(MainActivity.dict.get(Keys.DAILY_TASKS));

        addRepeatingNotificationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MoreOptionsMenu.this, RepeatingNotificationMenu.class));
            }
        });

        addDailyTasksButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MoreOptionsMenu.this, DailyTasksMenu.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MoreOptionsMenu.this, MainCalendar.class));
            }
        });
    }
}
