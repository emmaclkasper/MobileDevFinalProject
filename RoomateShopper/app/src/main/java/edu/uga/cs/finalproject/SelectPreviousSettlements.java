package edu.uga.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectPreviousSettlements extends AppCompatActivity implements RecyclerSettlementAdapter.SettlementListener {

    ArrayList<PreviousCosts> previousCostsList = new ArrayList<>();
    DatabaseReference db;
    long length;
    private RecyclerSettlementAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_previous_settlements);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Previous Settlements");
        setSupportActionBar(toolbar);
        db = FirebaseDatabase.getInstance().getReference().child("Previous Scores");
        recyclerView = findViewById(R.id.previousSettlementsRecyclerView);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                length = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String date = dataSnapshot.child("Date").getValue().toString();
                String id = dataSnapshot.getKey();
                PreviousCosts currentSettlement = new PreviousCosts();
                currentSettlement.setDate(date);
                currentSettlement.setId(id);
                previousCostsList.add(currentSettlement);
                setupSettlementsRecycleView();
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

    public void setupSettlementsRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerSettlementAdapter(this, previousCostsList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onSettlementClick(int position) {
        Intent intent = new Intent(this, choosenPreviousSettlementActivity.class);
        intent.putExtra("selectedSettlement", previousCostsList.get(position));
        startActivity(intent);
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
