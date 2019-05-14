package com.rajat.alldemoapp.firebase;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajat.alldemoapp.R;

public class FireBaseActivity extends AppCompatActivity {

    private static final String NAME_KEY = "Name";
    private static final String EMAIL_KEY = "Email";
    private static final String PHONE_KEY = "Phone";
    private static final String TAG = FireBaseActivity.class.getSimpleName();

    //    FirebaseDatabase db;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //    FirebaseFirestore db;
    TextView textDisplay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_fire_base);
        FloatingActionButton fab = findViewById(R.id.fab);

//        writeToDB();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeDB();
                Snackbar.make(view, "Click", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                readDataFromDb();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                initializeDB();
                Snackbar.make(v, "Long Click", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                writeToDB();
                return false;
            }
        });
    }

    private void initializeDB() {
        if(firebaseDatabase==null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
    }

    private void readDataFromDb() {
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void writeToDB() {
        databaseReference = firebaseDatabase.getReference("message");
        databaseReference.setValue("Test Message");
    }
}
