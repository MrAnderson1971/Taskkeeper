package com.example.myapplication.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ConfirmDeleteDialog extends AppCompatDialogFragment
{

    private ConfirmDeleteDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle(MainActivity.dict.get(Keys.CONFIRM_DELETION))
                .setNegativeButton(Languages.CROSSMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.applyDeletion(false);
                    }
                })
                .setPositiveButton(Languages.CHECKMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.applyDeletion(true);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            listener = (ConfirmDeleteDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement ConfirmDeleteDialogListener");
        }
    }

    public interface ConfirmDeleteDialogListener
    {
        void applyDeletion(boolean arg);
    }
}