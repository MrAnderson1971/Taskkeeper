package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.DataManager;
import com.example.myapplication.Classes.Heap;
import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Reminder;
import com.example.myapplication.Dialogs.ConfirmDeleteDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RemindersSummary extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    private ArrayList<Reminder> mergedRemindersArray;

    private Spinner allRemindersSpinner;

    private Reminder selectedReminder;

    private String[] sortingMethods;
    private int sortingMethodIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_summary);
        setTitle("");

        Reminder.displayDateInString = true;
        sortingMethods = new String[]{
                MainActivity.dict.get(Keys.DATE), MainActivity.dict.get(Keys.NAME)
        };
        sortingMethodIndex = 0;

        allRemindersSpinner = findViewById(R.id.allRemindersSpinner);

        Button backButton = findViewById(R.id.backButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button gotoDateButton = findViewById(R.id.gotoButton);
        TextView sortByText = findViewById(R.id.sortByText);
        final Button sortByButton = findViewById(R.id.sortByButton);

        backButton.setText(MainActivity.dict.get(Keys.BACK));
        deleteButton.setText(MainActivity.dict.get(Keys.DELETE));
        gotoDateButton.setText(MainActivity.dict.get(Keys.GO_TO_DATE));
        sortByText.setText(MainActivity.dict.get(Keys.SORT_BY));
        sortByButton.setText(sortingMethods[0]);

        setArrayList();

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RemindersSummary.this, MainCalendar.class));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selected = allRemindersSpinner.getSelectedItem();
                if (selected == null)
                {
                    Toast.makeText(RemindersSummary.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                } else
                {
                    selectedReminder = (Reminder) selected;
                    openDeletionDialog();
                }
            }
        });

        gotoDateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Object selected = allRemindersSpinner.getSelectedItem();
                if (selected == null)
                {
                    Toast.makeText(RemindersSummary.this, MainActivity.dict.get(Keys.NOTHING_SELECTED), Toast.LENGTH_SHORT).show();
                } else
                {
                    selectedReminder = (Reminder) selected;
                    MainCalendar.selectedDate = selectedReminder.getDateAndTime();
                    startActivity(new Intent(RemindersSummary.this, AddNewNotificationMenu.class));
                }
            }
        });

        sortByButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sortingMethodIndex++;
                String newSortingMethod = sortingMethods[sortingMethodIndex % sortingMethods.length];
                sortByButton.setText(newSortingMethod);

                for (long l : MainCalendar.allReminders.keySet())
                {
                    try
                    {
                        Collections.sort(MainCalendar.allReminders.get(l), Heap.getComparator(newSortingMethod).newInstance());
                    } catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    } catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                }
                setArrayList(Heap.getComparator(newSortingMethod));
                updateSpinner();
            }
        });

        updateSpinner();
    }

    public void setArrayList()
    {
        setArrayList(Heap.getComparator(""));
    }

    public void setArrayList(Class<? extends Comparator> comparator)
    {
        ArrayList<ArrayList<? extends Comparable>> arr = new ArrayList<>();
        for (long l : MainCalendar.allReminders.keySet())
        {
            arr.add(MainCalendar.allReminders.get(l));
        }
        try
        {
            mergedRemindersArray = (ArrayList<Reminder>) Heap.merge(arr, comparator.newInstance());
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        }
    }

    private void updateSpinner()
    {
        ArrayAdapter<Reminder> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mergedRemindersArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allRemindersSpinner.setAdapter(adapter);
    }

    private void openDeletionDialog()
    {
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
        dialog.show(getSupportFragmentManager(), "Delete");
    }

    @Override
    public void applyDeletion(boolean arg)
    {
        if (arg)
        {
            MainCalendar.allReminders.get(selectedReminder.getDateAndTime().hashDateWithoutTime()).remove(selectedReminder);
            DataManager.save(this, MainCalendar.allReminders, MainActivity.activeUser.getReminderSaveFileName());
            selectedReminder = null;
            setArrayList();
            updateSpinner();
        }
    }
}
