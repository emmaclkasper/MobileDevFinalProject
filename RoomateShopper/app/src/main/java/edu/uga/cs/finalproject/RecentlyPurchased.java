package edu.uga.cs.finalproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecentlyPurchased extends AppCompatActivity {

    DatabaseReference db;
    RecyclerView recyclerView;
    ArrayList<Details> list;
    Adapter adapter;
    Button button;

    public static final String DEBUG_TAG = "Recently purchased";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_purchased);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Recently Purchased");
        setSupportActionBar(toolbar);

        Log.d(DEBUG_TAG, "on create ");

        button = findViewById(R.id.settle_score);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //yes
                                Intent intent = new Intent(RecentlyPurchased.this, SettlingTheCost.class);
                                v.getContext().startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No
                                break;

                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
                builder.setMessage("Are you sure you want to settle the score? Settling the score" +
                        " would remove all items in recently purchased.").setPositiveButton("Yes", dialogListener).setNegativeButton("No", dialogListener).show();

            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Details>();

        db = FirebaseDatabase.getInstance().getReference().child("Purchased");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i(DEBUG_TAG, "on data change ");

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Details d = dataSnapshot1.getValue(Details.class);
                    list.add(d);
                }

                adapter = new Adapter(RecentlyPurchased.this, list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d(DEBUG_TAG, "on cancel ");
                Toast.makeText(RecentlyPurchased.this, "Something is wrong", Toast.LENGTH_SHORT);

            }
        });

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
