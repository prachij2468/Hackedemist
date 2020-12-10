package com.hackedemist.app.Database;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackedemist.app.Level_start;
import com.termux.R;

import androidx.annotation.NonNull;

public class FirebaseLogin extends Activity {


    private EditText userName;
    private EditText userPass;
    private Button loginBtn;
    private TextView register;
    private Boolean userCheck = false;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //Preferences
    SharedPreferences prefs;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        userName = (EditText) findViewById(R.id.userNameLogin);
        userPass = (EditText) findViewById(R.id.userPassLogin);
        loginBtn = (Button) findViewById(R.id.usrSubmitLogin);
        register = (TextView) findViewById(R.id.Register_login);

        //Preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(FirebaseLogin.this);
        sharedPreferences = getSharedPreferences(Level_start.SHARED_PREFS,MODE_PRIVATE);


        //FirebaseConnect
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserDetails");

        if(userName.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCheck=false;
                String nameEntered = userName.getText().toString().trim();
                String passEntered = userPass.getText().toString().trim();


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String firebasePass=null;
                        String firebaseId=null;
                        String firebaseCoins="0";
                        int hd_levelTrack = 0;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                        {
                            UserData user = userSnapshot.getValue(UserData.class);



                            if (user.getUserName().equals(nameEntered) && !nameEntered.equals(""))
                            {
                                userCheck = true;
                                 firebasePass = user.getUserPass();
                                 firebaseId = user.getUserId();
                                 firebaseCoins = user.getUserCoins();
                                 hd_levelTrack = user.getHd_LevelTrack();

                                break;
                            }else{
                                userCheck=false;
                            }


                        }

                        if (userCheck)
                        {
                            if (firebasePass.equals(passEntered) && !passEntered.equals(""))
                            {
                                //Editing Preferences
                                prefs.edit().putString("id",firebaseId).apply();
                                prefs.edit().putBoolean("userAdded",true).apply();
                                prefs.edit().putInt("hd_levelTrack",hd_levelTrack).apply();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Level_start.COINS, firebaseCoins);
                                editor.apply();
                                //Finish


                                Toast.makeText(FirebaseLogin.this,"Welcome "+nameEntered,Toast.LENGTH_LONG).show();

                                startActivity(new Intent(FirebaseLogin.this,Level_start.class));
                            finish();
                            }
                            else {
                                Toast.makeText(FirebaseLogin.this,"Username or Password is Incorrect",Toast.LENGTH_LONG).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(FirebaseLogin.this,"Username or Password is Incorrect",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogin.this,DatabaseConnect.class));
            }
        });



    }
}
