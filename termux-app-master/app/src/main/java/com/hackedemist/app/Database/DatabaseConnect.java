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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackedemist.app.Level_start;
import com.termux.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class DatabaseConnect extends Activity {

    private EditText userName;
    private EditText userPass;
    private TextView userExistsView;
    private Button submit;
    private String coins;
    private FirebaseDatabase database;
    private SharedPreferences prefs;
    private List<UserData> userList;
    private Boolean userCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_database_connect);
        FirebaseApp.initializeApp(this);

        //shared prefs
        SharedPreferences sharedPreferences = getSharedPreferences(Level_start.SHARED_PREFS, MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(DatabaseConnect.this);

        userName = (EditText) findViewById(R.id.userName);
        userPass = (EditText) findViewById(R.id.userPass);
        submit = (Button) findViewById(R.id.usrSubmit);
        coins = sharedPreferences.getString(Level_start.COINS, "200");
        userExistsView = (TextView) findViewById(R.id.userNameExists);

        if(userName.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        userList = new ArrayList<>();

        //Shared Prefs for coins
        // Write a message to the database

        database = FirebaseDatabase.getInstance();

//        String user = userName.getText().toString();
//
//        DatabaseReference myRef = database.getReference("username");
//
//        myRef.setValue(user);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference myRef = database.getReference("UserDetails");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        userList.clear();
                        String usr = userName.getText().toString().trim();

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                        {
                            UserData user = userSnapshot.getValue(UserData.class);

                            userList.add(user);
                            System.out.print("Username = " + user.getUserName());

                            if (user.getUserName().equals(usr))
                            {
                                userCheck = true;
                                userExistsView.setVisibility(userExistsView.VISIBLE);
                                break;
                            }else{
                                userCheck=false;
                            }


                        }

                        if (!userCheck) {
                            putData();
                            startActivity(new Intent(DatabaseConnect.this, Level_start.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//


            }
        });

    }


    void putData() {
        DatabaseReference myRef = database.getReference("UserDetails");
        String id = myRef.push().getKey();
        prefs.edit().putString("id", id).apply();
        prefs.edit().putBoolean("userAdded",true).apply();
        String usr = userName.getText().toString().trim();
        String pass = userPass.getText().toString().trim();

        UserData user = new UserData(id, usr, coins, pass, 0);

        myRef.child(id).setValue(user);
    }

    void checkUserPresent() {

    }
}
