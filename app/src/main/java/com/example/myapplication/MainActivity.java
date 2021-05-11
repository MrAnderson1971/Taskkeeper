package com.example.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Language;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.Classes.User;
import com.example.myapplication.Dialogs.ConfirmDeleteDialog;
import com.example.myapplication.Dialogs.LanguageSelectionDialog;
import com.example.myapplication.Dialogs.RenameDialog;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.ConfirmDeleteDialogListener, RenameDialog.RenameDialogListener, LanguageSelectionDialog.LanguageSelectionDialogListener
{

    public static final String LANG_SETTINGS_FILE_PATH = "lang_settings.txt";
    public static final String USERS_FILE_PATH = "users.txt";
    //public static final String LAST_ACCESSSED_FILE_PATH = "lastaccessed.txt";

    public static Language lang = Language.ENGLISH;
    public static Languages dict = new Languages();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<String> usernames = new ArrayList<>();
    public static User activeUser;

    private static Object delete;
    private static Object rename;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        activeUser = null;

        String load = "";
        // load existing language settings
        if (DataManager.fileExists(this, LANG_SETTINGS_FILE_PATH))
        {
            load = (String) DataManager.load(this, LANG_SETTINGS_FILE_PATH);
        }

        // load all users
        if (DataManager.fileExists(this, USERS_FILE_PATH))
        {
            users = (ArrayList<User>) DataManager.load(this, USERS_FILE_PATH);
            Collections.sort(users);
        }

        //Toast.makeText(this, Boolean.toString(checkDate()), Toast.LENGTH_LONG).show(); // TODO

        switch (load)  // use switch statement to facilitate the addition of new languages
        {
            case "ENGLISH":
                lang = Language.ENGLISH;
                break;
            case "CHINESE":
                lang = Language.CHINESE;
                break;
            default:
                lang = Language.ENGLISH;
        }

        final ImageButton languageButton = findViewById(R.id.languageButton);
        final TextView selectExistingUserText = findViewById(R.id.selectExistingUserText);
        final Button newUserButton = findViewById(R.id.newUserButton);
        final Spinner existingUserSpinner = findViewById(R.id.existingUserSpinner);
        final Button deleteUserButton = findViewById(R.id.deleteUserButton);
        final Button proceedButton = findViewById(R.id.proceedButton);
        final Button renameButton = findViewById(R.id.renameButton);
        Button helpButton = findViewById(R.id.helpButton);

        //languageButton.setText((lang == Language.ENGLISH) ? "中文" : "English");

        renameButton.setText(dict.get(Keys.RENAME));
        proceedButton.setText(dict.get(Keys.PROCEED));
        selectExistingUserText.setText(dict.get(Keys.SELECT_EXISTING_USER));
        newUserButton.setText(dict.get(Keys.NEW_USER));
        deleteUserButton.setText(dict.get(Keys.DELETE_USER));
        helpButton.setText(dict.get(Keys.HELP));

        // load all users
        usernames = new ArrayList<>();
        for (User u : users)
        {
            usernames.add(u.getName());
        }

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        existingUserSpinner.setAdapter(adapter);

        languageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*lang = (lang == Language.ENGLISH) ? Language.CHINESE : Language.ENGLISH;  // switch to opposite language

                //languageButton.setText((lang == Language.ENGLISH) ? "中文" : "English");

                renameButton.setText(dict.get(Keys.RENAME));
                proceedButton.setText(dict.get(Keys.PROCEED));
                selectExistingUserText.setText(dict.get(Keys.SELECT_EXISTING_USER));
                newUserButton.setText(dict.get(Keys.NEW_USER));
                deleteUserButton.setText(dict.get(Keys.DELETE_USER));*/
                openLanguageDialog();

            }
        });

        newUserButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, NewUserScreen.class));
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delete = existingUserSpinner.getSelectedItem();
                if (delete != null)
                {
                    openDeleteDialog();
                } else
                {
                    Toast.makeText(MainActivity.this, dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                }
            }
        });

        renameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rename = existingUserSpinner.getSelectedItem();
                if (rename != null)
                {
                    openRenameDialog();
                } else
                {
                    Toast.makeText(MainActivity.this, dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                }
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selection = existingUserSpinner.getSelectedItem();
                if (selection != null)
                {
                    activeUser = (User) selection;
                    activeUser.updateLastAccessed();
                    DataManager.save(MainActivity.this, users, USERS_FILE_PATH);
                    startActivity(new Intent(MainActivity.this, MainCalendar.class)); // reload the spinner after deletion
                } else
                {
                    Toast.makeText(MainActivity.this, dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                }
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, HelpPage.class));
            }
        });

    }

    private void openDeleteDialog()
    {
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
        dialog.show(getSupportFragmentManager(), "Delete");
    }

    private void openRenameDialog()
    {
        RenameDialog dialog = new RenameDialog();
        dialog.show(getSupportFragmentManager(), "Rename");
    }

    private void openLanguageDialog()
    {
        LanguageSelectionDialog dialog = new LanguageSelectionDialog();
        dialog.show(getSupportFragmentManager(), "Language");
    }

    @Override
    public void applyDeletion(boolean arg)
    {
        if (arg)
        {
            User deleteUser = (User) delete;

            String prevFilePath = deleteUser.getReminderSaveFileName();
            String prevRepeatingPath = deleteUser.getRepeatingNotificationSaveFileName();
            String prevDailyPath = deleteUser.getDailyTaskSaveFileName();

            users.remove(deleteUser);
            DataManager.deleteFile(MainActivity.this, prevFilePath);
            DataManager.deleteFile(MainActivity.this, prevRepeatingPath);
            DataManager.deleteFile(MainActivity.this, prevDailyPath);
            DataManager.save(MainActivity.this, users, USERS_FILE_PATH);
            activeUser = null;
            startActivity(new Intent(MainActivity.this, MainActivity.class)); // reload the spinner after deletion
        }
    }

    @Override
    public void applyRename(String newName)
    {
        String toastText;

        if (newName.length() == 0)
        {
            toastText = dict.get(Keys.PLEASE_FILL_IN_THE_BLANKS);
        } else if (MainActivity.usernames.contains(newName))
        {
            toastText = dict.get(Keys.NAME_TAKEN);
        } else
        {
            User renameUser = (User) rename;

            String prevFilePath = renameUser.getReminderSaveFileName();
            String prevRepeatingPath = renameUser.getRepeatingNotificationSaveFileName();
            String prevDailyPath = renameUser.getDailyTaskSaveFileName();

            toastText = dict.get(Keys.SUCCESSFUL);
            //User.getUserByName(users, renameUsername).setName(newName);
            renameUser.setName(newName);

            DataManager.save(MainActivity.this, MainActivity.users, USERS_FILE_PATH);

            // rename save file as well
            DataManager.renameFile(MainActivity.this, prevFilePath, renameUser.getReminderSaveFileName());
            DataManager.renameFile(MainActivity.this, prevRepeatingPath, renameUser.getRepeatingNotificationSaveFileName());
            DataManager.renameFile(MainActivity.this, prevDailyPath, renameUser.getDailyTaskSaveFileName());
            activeUser = null;
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyLanguageSelection(Language l)
    {
        lang = l;
        DataManager.save(MainActivity.this, lang.toString(), LANG_SETTINGS_FILE_PATH);
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }
}
