package com.example.sohanagarkar.timetickets;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
    private long childrenCount;
    private int[] arrPoints;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        DatabaseReference fb = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
       DatabaseReference players = fb.child("/codes/" + code + "/players");

       players.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                childrenCount = dataSnapshot.getChildrenCount();
                int childrenC = (int) childrenCount;
                arrPoints = new int[childrenC];
                TextView playerOne = (TextView) findViewById(R.id.points_1);
                TextView playerTwo = (TextView) findViewById(R.id.points_2);
                TextView nameOne = (TextView) findViewById(R.id.name_1);
               TextView nameTwo = (TextView) findViewById(R.id.name_2);
               String valueQuery;
               for(int i = 0; i < childrenC; i++) {
                   valueQuery = dataSnapshot.child(i+1 + "/points").getValue().toString();
                   arrPoints[i] = Integer.parseInt(valueQuery);
               }
                playerOne.setText(dataSnapshot.child("1/points").getValue().toString());
               playerTwo.setText(dataSnapshot.child("2/points").getValue().toString());
               nameOne.setText(dataSnapshot.child("1/name").getValue().toString());
               nameTwo.setText(dataSnapshot.child("2/name").getValue().toString());
               //TODO get player values from dynamic counting system i.e. "1/points", "2/points", "3/points" systematically
               //TODO change value location into ListView with "name(number) - value"
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
    //TODO writing function for up
    public void Uplayers(View view) {
        EditText PlayerNumber = (EditText) findViewById(R.id.player_number);
        EditText PointNumber = (EditText) findViewById(R.id.points_number);
        String playerNum = PlayerNumber.getText().toString();
        String pointNum = PointNumber.getText().toString();
        if(isNumber(playerNum) == true) {

            if(isNumber(pointNum) == true) {
                int cCount = (int) childrenCount;
                if(Integer.parseInt(playerNum) <= cCount) {
                    DatabaseReference fbt = FirebaseDatabase.getInstance().getReference();
                    try {
                        fbt.child("codes").child(code).child("players").child(playerNum).child("points").setValue(arrPoints[Integer.parseInt(playerNum)-1] + Integer.parseInt(pointNum));
                        refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("something went wrong");
                    }
                }
            }
            else {
                toast("Make sure both of the lines are numbers");
            }
        }
        else {
            toast("Make sure both of the lines are numbers");
        }
    }
    public boolean isNumber (String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public void dPlayers(View view) {
        EditText PlayerNumber = (EditText) findViewById(R.id.player_number);
        EditText PointNumber = (EditText) findViewById(R.id.points_number);
        String playerNum = PlayerNumber.getText().toString();
        String pointNum = PointNumber.getText().toString();
        if(isNumber(playerNum) == true) {

            if(isNumber(pointNum) == true) {
                int cCount = (int) childrenCount;
                if(Integer.parseInt(playerNum) <= cCount) {
                    DatabaseReference fbt = FirebaseDatabase.getInstance().getReference();
                    try {
                        fbt.child("codes").child(code).child("players").child(playerNum).child("points").setValue(arrPoints[Integer.parseInt(playerNum)-1] - Integer.parseInt(pointNum));
                        refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("something went wrong");
                    }
                }
            }
            else {
                toast("Make sure both of the lines are numbers");
            }
        }
        else {
            toast("Make sure both of the lines are numbers");
        }
    }
    //TODO refresh button
    public void refresh() {
        DatabaseReference fbtr = FirebaseDatabase.getInstance().getReference();
        DatabaseReference players = fbtr.child("codes").child(code).child("players");
        players.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrenCount = dataSnapshot.getChildrenCount();
                int childrenC = (int) childrenCount;
                arrPoints = new int[childrenC];
                TextView playerOne = (TextView) findViewById(R.id.points_1);
                TextView playerTwo = (TextView) findViewById(R.id.points_2);
                TextView nameOne = (TextView) findViewById(R.id.name_1);
                TextView nameTwo = (TextView) findViewById(R.id.name_2);
                String valueQuery;
                for(int i = 0; i < childrenC; i++) {
                    valueQuery = dataSnapshot.child(i+1 + "/points").getValue().toString();
                    arrPoints[i] = Integer.parseInt(valueQuery);
                }
                playerOne.setText(dataSnapshot.child("1/points").getValue().toString());
                playerTwo.setText(dataSnapshot.child("2/points").getValue().toString());
                nameOne.setText(dataSnapshot.child("1/name").getValue().toString());
                nameTwo.setText(dataSnapshot.child("2/name").getValue().toString());
                //TODO get player values from dynamic counting system i.e. "1/points", "2/points", "3/points" systematically
                //TODO change value location into ListView with "name(number) - value"
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

    public void refreshValue(View view) {
        refresh();
    }

    //TODO writing function for down
}
