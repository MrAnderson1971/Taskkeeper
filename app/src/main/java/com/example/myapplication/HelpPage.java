package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.myapplication.Classes.Keys;

public class HelpPage extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        setTitle(MainActivity.dict.get(Keys.HELP));

        Button backButton = findViewById(R.id.backButton);
        WebView webView = findViewById(R.id.webView);

        backButton.setText(MainActivity.dict.get(Keys.BACK));

        String htmlFilename;
        switch (MainActivity.lang)
        {
            case ENGLISH:
                htmlFilename = "enhelp.html";
                break;
            case CHINESE:
                htmlFilename = "zhhelp.html";
                break;
            default:
                throw new IllegalArgumentException("Language not supported.");
        }

        webView.loadUrl("file:///android_asset/" + htmlFilename);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HelpPage.this, MainActivity.class));
            }
        });
    }
}
