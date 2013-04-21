package com.example.Mo_Banking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity
{
    private Button newAccountButton;
    private Button seeStatusButton;
    private Intent newViewIntent;
    private Intent statusViewIntent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        newAccountButton = (Button) findViewById(R.id.bt_main_new);
        seeStatusButton  = (Button) findViewById(R.id.bt_main_status);

        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newViewIntent = new Intent(getApplicationContext(),NewAccountView.class);
                startActivity(newViewIntent);
            }
        });

        seeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(statusViewIntent);
                statusViewIntent = new Intent(getApplicationContext(), SeeStatusView.class);
            }
        });
    }
}
