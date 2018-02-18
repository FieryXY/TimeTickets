package com.example.sohanagarkar.timetickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {
private String passcode = "hunka";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkPasscode(View view) {
       EditText coder = (EditText) findViewById(R.id.codeword);
       EditText passcoder = (EditText) findViewById(R.id.password);
       final String passguess = passcoder.getText().toString();
       final String guess = coder.getText().toString();
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference codeTable = firebase.child("codes");
        codeTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(guess)) {


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           /* User user = snapshot.getValue(User.class);
                            System.out.println(user.email);*/
                           if(snapshot.child("password").getValue().equals(passguess)) {
                               Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                               intent.putExtra("code", guess);
                               startActivity(intent);
                                toast("Correct! This is WIP");
                           }
                           else {
                               toast("Incorrect Password");
                           }
                        }

                }
                else{
                    toast("Invalid Code");
                    //TODO create "Code Maker" activity and add dynamic players in it
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /*if(guess.equals(passcode)) {
           Intent intent = new Intent(this, InformationActivity.class);
           intent.putExtra("code-name", "hunka");
           startActivity(intent);
       }
       else {
           passcoder.setText(guess + " is wrong");
       }*/

    }
}
