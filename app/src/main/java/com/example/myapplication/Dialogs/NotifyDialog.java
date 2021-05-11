package com.example.myapplication.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Classes.Languages;
import com.example.myapplication.R;

public class NotifyDialog extends AppCompatDialogFragment
{

    private String title;
    private String text;
    private String next;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_notify_dialog, null);

        builder.setView(view)
                .setTitle(title)
                .setPositiveButton(Languages.CHECKMARK, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });

        TextView notificationText = view.findViewById(R.id.notificationText);
        TextView nextText = view.findViewById(R.id.nextNotificationText);

        notificationText.setText(text);
        nextText.setText(next);

        return builder.create();
    }

    public void setNotification(String title, String text)
    {
        setNotification(title, text, "");
    }

    public void setNotification(String title, String text, String next)
    {
        this.title = title;
        this.text = text;
        this.next = next;
    }
}