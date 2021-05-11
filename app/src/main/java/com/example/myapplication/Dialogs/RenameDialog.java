package com.example.myapplication.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Classes.Keys;
import com.example.myapplication.Classes.Languages;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class RenameDialog extends AppCompatDialogFragment
{

    private RenameDialogListener listener;

    private EditText newUsername;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_rename_dialog, null);


        builder.setView(view)
                .setTitle(MainActivity.dict.get(Keys.RENAME))
                .setNegativeButton(Languages.CROSSMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).setPositiveButton(Languages.CHECKMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                       String newName = newUsername.getText().toString();
                       listener.applyRename(newName);
                    }
                });

        newUsername = view.findViewById(R.id.newUsername);
        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            listener = (RenameDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement RenameDialogListener");
        }
    }

    public interface RenameDialogListener
    {
        void applyRename(String newName);
    }
}
