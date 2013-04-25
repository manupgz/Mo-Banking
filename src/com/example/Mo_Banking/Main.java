package com.example.Mo_Banking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import com.database.Mo_Banking.DatabaseHandler;
import com.definitions.Mo_Banking.Account;

import java.io.*;
import java.util.HashMap;

public class Main extends Activity
{
    private Button newAccountButton;
    private Button seeStatusButton;
    private Intent newViewIntent;
    private Intent statusViewIntent;

    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private Time today;

    private static final String PREFERENCES_FILENAME = "Mo-Banking-Preferences";
    private static final String FILENAME             = "saveddata";

    private DatabaseHandler databaseHandler;
    public static HashMap<Integer,Account> accounts;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        preferences = getSharedPreferences(PREFERENCES_FILENAME,0);

        /**
         * If the app is runned for the first time edit the preferences and prepare files.
         */
        if(preferences.getBoolean("first_run",true))
            prepareFirstRun();
        else {
            accounts = new HashMap<Integer, Account>();
            databaseHandler = new DatabaseHandler(this);
            accounts = databaseHandler.getAccounts();

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
                    statusViewIntent = new Intent(getApplicationContext(), SeeStatusView.class);
                    startActivity(statusViewIntent);
                }
            });
        }
    }
    public void prepareFirstRun() {
        try {
            editor = preferences.edit();
            editor.putBoolean("first_run",false);
            setDirectory();
            editor.commit();
        }
        catch (FileNotFoundException e)  {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates the file in private mode in external storage if available,
     * it also sets a shared setting for later finding the file from other activities.
     * This function is only called on the first run of the app.
     *
     * @throws FileNotFoundException
     */
    private void setDirectory() throws IOException {
        today = new Time();
        today.setToNow();

        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(String.format("#App Installed: %s", today).getBytes());
        fos.close();

    }
}
