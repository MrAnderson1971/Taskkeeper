package com.example.myapplication.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Language;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class LanguageSelectionDialog extends AppCompatDialogFragment
{

    private LanguageSelectionDialogListener listener;

    private ArrayList<CheckBox> allCheckboxes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_language_selection_dialog, null);

        final CheckBox englishCheckbox = view.findViewById(R.id.englishCheckbox);
        final CheckBox chineseCheckbox = view.findViewById(R.id.chineseCheckbox);

        builder.setView(view)
                //.setTitle(Languages.GLOBE)
                .setNegativeButton(Languages.CROSSMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                })
                .setPositiveButton(Languages.CHECKMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        switch (getCheckedCheckbox())
                        {
                            case "English":
                                listener.applyLanguageSelection(Language.ENGLISH);
                                break;
                            case "中文":
                                listener.applyLanguageSelection(Language.CHINESE);
                                break;
                            default:
                                throw new UnsupportedOperationException("Language not recognized");
                        }
                    }
                });

        switch (MainActivity.lang)
        {
            case ENGLISH:
                englishCheckbox.setChecked(true);
                break;
            case CHINESE:
                chineseCheckbox.setChecked(true);
        }

        allCheckboxes = new ArrayList<>(Arrays.asList(englishCheckbox, chineseCheckbox));

        /*englishCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    disableOtherCheckboxes(englishCheckbox);
                }
            }
        });
        chineseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    disableOtherCheckboxes(chineseCheckbox);
                }
            }
        });*/

        englishCheckbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (englishCheckbox.isChecked())
                {
                    disableOtherCheckboxes(englishCheckbox);
                } else // make sure only one box is checked at a time
                {
                    englishCheckbox.setChecked(true);
                }
            }
        });

        chineseCheckbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chineseCheckbox.isChecked())
                {
                    disableOtherCheckboxes(chineseCheckbox);
                } else
                {
                    chineseCheckbox.setChecked(true);
                }
            }
        });

        return builder.create();
    }

    private void disableOtherCheckboxes(CheckBox activeCheckbox)
    {
        for (CheckBox c : allCheckboxes)
        {
            if (c != activeCheckbox)
            {
                c.setChecked(false);
            }
        }
    }

    private String getCheckedCheckbox()
    {
        for (CheckBox c : allCheckboxes)
        {
            if (c.isChecked())
            {
                return c.getText().toString();
            }
        }
        return "";
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            listener = (LanguageSelectionDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement LanguageSelectionDialogListener");
        }
    }

    public interface LanguageSelectionDialogListener
    {
        void applyLanguageSelection(Language l);
    }
}