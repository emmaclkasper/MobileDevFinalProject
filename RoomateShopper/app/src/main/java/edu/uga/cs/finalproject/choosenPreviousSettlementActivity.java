package edu.uga.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class choosenPreviousSettlementActivity extends AppCompatActivity {

    ArrayList<Roomates> roommatesList = new ArrayList<>();
    DatabaseReference db;
    private PreviousCosts previousCosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosen_previous_settlement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Previous Settlements");
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("selectedSettlement")){
            previousCosts = getIntent().getParcelableExtra("selectedSettlement");
        }

        System.out.println("WEFEWFIJEWFFEFFFFUCK " + previousCosts.getId());
        db = FirebaseDatabase.getInstance().getReference().child("Previous Scores").child(previousCosts.getId());

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("name").exists()) {
                    System.out.println("FUCK " + dataSnapshot.child("name").getValue().toString());
                    String name = dataSnapshot.child("name").getValue().toString();
                    String amountSpent = dataSnapshot.child("amountSpent").getValue().toString();
                    Roomates currentRoomate = new Roomates(name, amountSpent);
                    roommatesList.add(currentRoomate);
                    setupChoosenRoommatesListRecycleView();
                }
                else{

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setupChoosenRoommatesListRecycleView(){
        RecyclerView recyclerView = findViewById(R.id.chosenSettlement);
        SettlingTheCostRecyclerAdapter adapter = new SettlingTheCostRecyclerAdapter(this,roommatesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout:
                intent = new Intent(this,MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.previousSettlements:
                intent = new Intent(this,SelectPreviousSettlements.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
