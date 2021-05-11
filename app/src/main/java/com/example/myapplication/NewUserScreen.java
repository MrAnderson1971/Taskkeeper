package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.User;

public class NewUserScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(MainActivity.dict.get(Keys.ADD_NEW_USER));

        final EditText nameField = findViewById(R.id.nameField);
        nameField.setHint(MainActivity.dict.get(Keys.NAME));

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setText(MainActivity.dict.get(Keys.CONFIRM));

        Button backButton = findViewById(R.id.backButton);
        backButton.setText(MainActivity.dict.get(Keys.BACK));

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(NewUserScreen.this, MainActivity.class));
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newName = nameField.getText().toString();
                String toastText;

                if (newName.length() == 0)
                {
                    toastText = MainActivity.dict.get(Keys.PLEASE_FILL_IN_THE_BLANKS);
                } else if (MainActivity.usernames.contains(newName))
                {
                    toastText = MainActivity.dict.get(Keys.NAME_TAKEN);
                } else
                {
                    toastText = MainActivity.dict.get(Keys.NEW_USER_ADDED);
                    Toast.makeText(NewUserScreen.this, toastText, Toast.LENGTH_SHORT).show();
                    MainActivity.users.add(new User(newName));
                    DataManager.save(NewUserScreen.this, MainActivity.users, MainActivity.USERS_FILE_PATH);
                    startActivity(new Intent(NewUserScreen.this, MainActivity.class));
                }
                Toast.makeText(NewUserScreen.this, toastText, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
