package com.hackedemist.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;

import com.hackedemist.app.Database.DatabaseConnect;
import com.hackedemist.app.Database.FirebaseLogin;
import com.termux.R;

public class ActivitySplash extends Activity {


    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        prefs = PreferenceManager.getDefaultSharedPreferences(ActivitySplash.this);

        Thread myThread = new Thread()
        {
            @Override
            public void run() {
                try
                {

                    sleep(2000);
                    Boolean temp = prefs.getBoolean("userAdded",false);
                    Intent DatabaseConnect= new Intent(getApplicationContext(), FirebaseLogin.class);
                    Intent StartPage = new Intent(getApplicationContext(),Level_start.class);

                    if (!temp)
                    {
                        startActivity(DatabaseConnect);
                    }
                    else
                    {
                        startActivity(StartPage);
                    }
                    finish();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();

                }
            }
        };
        myThread.start();
    }

}
