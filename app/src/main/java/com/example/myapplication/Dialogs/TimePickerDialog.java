package com.example.myapplication.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class TimePickerDialog extends AppCompatDialogFragment
{

    private TimePickerDialogListener listener;

    private TimePicker timePicker;

    private int hour;
    private int minute;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_time_picker_dialog, null);

        builder.setView(view)
                .setTitle(MainActivity.dict.get(Keys.SELECT_TIME))
                .setNegativeButton(Languages.CROSSMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).setPositiveButton(Languages.CHECKMARK, new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        listener.applyTime(hour, minute);
                    }
                });

        timePicker = view.findViewById(R.id.timePicker);

        timePicker.setHour(hour);
        timePicker.setMinute(minute);

        timePicker.setIs24HourView(true);

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            listener = (TimePickerDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement TimePickerDialogListener");
        }
    }

    public interface TimePickerDialogListener
    {
        void applyTime(int hour, int minute);
    }

    public void setTime(int hour, int minute)
    {
        this.hour = hour;
        this.minute = minute;
    }
}
