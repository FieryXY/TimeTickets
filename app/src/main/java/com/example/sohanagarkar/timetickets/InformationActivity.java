package com.example.sohanagarkar.timetickets;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import stanford.androidlib.SimpleActivity;

public class InformationActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        DatabaseReference fb = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
       DatabaseReference players = fb.child("/codes/" + code + "/players");

       players.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                long childrenCount = dataSnapshot.getChildrenCount();
                TextView playerOne = (TextView) findViewById(R.id.points_1);
                TextView playerTwo = (TextView) findViewById(R.id.points_2);
                TextView nameOne = (TextView) findViewById(R.id.name_1);
               TextView nameTwo = (TextView) findViewById(R.id.name_2);

                playerOne.setText(dataSnapshot.child("1/points").getValue().toString());
               playerTwo.setText(dataSnapshot.child("2/points").getValue().toString());
               nameOne.setText(dataSnapshot.child("1/name").getValue().toString());
               nameTwo.setText(dataSnapshot.child("2/name").getValue().toString());
/*
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                  Log.d("child", snapshot.getKey().toString());
                   Log.d("test", snapshot.child("name").toString());


               }*/
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
}
