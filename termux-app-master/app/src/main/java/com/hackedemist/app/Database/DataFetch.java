package com.hackedemist.app.Database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.*;
import com.hackedemist.app.Level_start;
import com.termux.R;

import androidx.annotation.NonNull;

public class DataFetch extends Activity {

    private TextView coinsView;
    private TextView userNameView;
    private TextView idView;
    private String id;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fetch);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails");

        SharedPreferences sharedPreferences = getSharedPreferences(Level_start.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DataFetch.this);

        coinsView = (TextView) findViewById(R.id.coinsFetch);
        userNameView = (TextView) findViewById(R.id.nameFetch);
        idView =  (TextView) findViewById(R.id.idFetch);
        next = (Button) findViewById(R.id.fetchSubmit);

        id = prefs.getString("id","null");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataFetch.this, Level_start.class));
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserData user;
                user = dataSnapshot.child(id).getValue(UserData.class);

                coinsView.setText(user.userCoins);
                userNameView.setText(user.userName);
                idView.setText(user.userId);

                String coinsUpdated = user.getUserCoins();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Level_start.COINS,coinsUpdated);

                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
