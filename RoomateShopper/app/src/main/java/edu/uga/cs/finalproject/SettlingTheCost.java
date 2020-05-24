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
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class SettlingTheCost extends AppCompatActivity {

    ArrayList<Roomates> roommatesList = new ArrayList<>();
    DatabaseReference db;
    DatabaseReference storeSettleDB;
    private boolean contains;
    Button button;
    long maxId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settling_the_cost);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Shopping List");
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance().getReference().child("Purchased");
        storeSettleDB = FirebaseDatabase.getInstance().getReference().child("Previous Scores");
        button = findViewById(R.id.backToShoppingList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeSettleDB.child(String.valueOf(maxId+1)).setValue(roommatesList);
                String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                storeSettleDB.child(String.valueOf(maxId+1)).child("Date").setValue(date);
                db.removeValue();
                Intent intent = new Intent(SettlingTheCost.this, ShoppingList.class);
                v.getContext().startActivity(intent);
            }
        });

        storeSettleDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maxId = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String roommateName = dataSnapshot.child("roommateName").getValue().toString();
                String priceString = dataSnapshot.child("price").getValue().toString();
                if(roommatesList == null){
                    Roomates newRoommate = new Roomates(roommateName,priceString);
                    roommatesList.add(newRoommate);
                }
                else {
                    int position = 0;
                    for (int i = 0; i < roommatesList.size(); i++) {
                        if (roommatesList.get(i).getName().equalsIgnoreCase(roommateName)) {
                            position = i;
                            contains = true;
                            break;
                        }
                        else{
                            contains = false;
                        }
                    }
                    if (contains == true){
                        Double currentPrice = Double.parseDouble(priceString);
                        Double currentRoommatePrice = Double.parseDouble(roommatesList.get(position).getAmountSpent()) + currentPrice;
                        String newTotal = String.valueOf(currentRoommatePrice);
                        roommatesList.get(position).setAmountSpent(newTotal);
                    }
                    else if (contains == false){
                        Roomates newRoommate = new Roomates(roommateName, priceString);
                        roommatesList.add(newRoommate);
                    }
                }
                setupRoommatesListRecycleView();
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

    public void setupRoommatesListRecycleView(){
        RecyclerView recyclerView = findViewById(R.id.roommateRecyclerView);
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
                storeSettleDB.child(String.valueOf(maxId+1)).setValue(roommatesList);
                String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                storeSettleDB.child(String.valueOf(maxId+1)).child("Date").setValue(date);
                db.removeValue();
                intent = new Intent(this,MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.previousSettlements:
                storeSettleDB.child(String.valueOf(maxId+1)).setValue(roommatesList);
                date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                storeSettleDB.child(String.valueOf(maxId+1)).child("Date").setValue(date);
                db.removeValue();
                intent = new Intent(this,SelectPreviousSettlements.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {//DO NOTHING
        /*storeSettleDB.child(String.valueOf(maxId+1)).setValue(roommatesList);
        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        storeSettleDB.child(String.valueOf(maxId+1)).child("Date").setValue(date);
        db.removeValue();
        Intent intent = new Intent(SettlingTheCost.this, ShoppingList.class);
        startActivity(intent);*/
    }


}
