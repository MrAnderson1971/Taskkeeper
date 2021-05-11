package com.example.myapplication.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class WeekSelectionDialog extends AppCompatDialogFragment
{

    private WeekSelectionDialogListener listener;

    private CheckBox sunday;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;

    private ArrayList<Integer> selectedDays;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_day_of_week_dialog, null);

        builder.setView(view)
            //.setTitle(MainActivity.dict.get(Keys.CONFIRM))
            .setPositiveButton(Languages.CHECKMARK, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.applyWeekSelection(getCheckBoxes());
                }
            }).setNegativeButton(Languages.CROSSMARK, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });

        sunday = view.findViewById(R.id.sundayCheckbox);
        monday = view.findViewById(R.id.mondayCheckbox);
        tuesday = view.findViewById(R.id.tuesdayCheckbox);
        wednesday = view.findViewById(R.id.wednesdayCheckbox);
        thursday = view.findViewById(R.id.thursdayCheckbox);
        friday = view.findViewById(R.id.fridayCheckbox);
        saturday = view.findViewById(R.id.saturdayCheckbox);

        sunday.setText(MainActivity.dict.get(Keys.SUNDAY));
        monday.setText(MainActivity.dict.get(Keys.MONDAY));
        tuesday.setText(MainActivity.dict.get(Keys.TUESDAY));
        wednesday.setText(MainActivity.dict.get(Keys.WEDNESDAY));
        thursday.setText(MainActivity.dict.get(Keys.THURSDAY));
        friday.setText(MainActivity.dict.get(Keys.FRIDAY));
        saturday.setText(MainActivity.dict.get(Keys.SATURDAY));

        sunday.setChecked(selectedDays.contains(1));
        monday.setChecked(selectedDays.contains(2));
        tuesday.setChecked(selectedDays.contains(3));
        wednesday.setChecked(selectedDays.contains(4));
        thursday.setChecked(selectedDays.contains(5));
        friday.setChecked(selectedDays.contains(6));
        saturday.setChecked(selectedDays.contains(7));

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            listener = (WeekSelectionDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement WeekSelectionDialogListener");
        }
    }

    public interface WeekSelectionDialogListener
    {
        void applyWeekSelection(ArrayList<Integer> selectedDays);
    }

    private ArrayList<Integer> getCheckBoxes()
    {
        ArrayList<Integer> days = new ArrayList<>();
        if (sunday.isChecked())
        {
            days.add(1);
        }
        if (monday.isChecked())
        {
            days.add(2);
        }
        if (tuesday.isChecked())
        {
            days.add(3);
        }
        if (wednesday.isChecked())
        {
            days.add(4);
        }
        if (thursday.isChecked())
        {
            days.add(5);
        }
        if (friday.isChecked())
        {
            days.add(6);
        }
        if (saturday.isChecked())
        {
            days.add(7);
        }
        return days;
    }

    public void setCheckBoxes(ArrayList<Integer> selectedDays)
    {
        this.selectedDays = selectedDays;
    }
}
