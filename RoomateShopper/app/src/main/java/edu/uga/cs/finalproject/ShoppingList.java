package edu.uga.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ShoppingList extends AppCompatActivity implements AddDialog.AddDialogListener, PurchasedItemDialog.PurchasedItemDialogListener {

    ArrayList<Item> items = null;

    private RecyclerView recyclerView;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    FloatingActionButton addItem;
    Button recentPurchase;

    DatabaseReference db;
    long maxId;
    int numberOfItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Shopping List");
        setSupportActionBar(toolbar);
        items = new ArrayList<>();
        maxId = 0;
        numberOfItems = 0;
        recyclerView = findViewById(R.id.itemsRecyclerView);
        addItem = findViewById(R.id.add);
        recentPurchase = findViewById(R.id.reviewRecentlyPurchased);
        recentPurchase.setOnClickListener(new RecentButtonClickListener());
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        db = FirebaseDatabase.getInstance().getReference().child("Item");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    //maxId = dataSnapshot.getChildrenCount(); Start at empty. if error, then its needed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String itemName = dataSnapshot.child("itemName").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String id = dataSnapshot.getKey();
                Item currentItem = new Item();
                currentItem.setItemName(itemName);
                currentItem.setId(id);
                currentItem.setDate(date);
                items.add(currentItem);
                System.out.println("WEFOIJ " + items.size());
                maxId= Long.valueOf(items.get(items.size()-1).getId());
                setupShoppingListRecycleView();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getKey().toString();
                String item = dataSnapshot.child("itemName").getValue().toString();
                int position = 0;
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getId().equalsIgnoreCase(dataSnapshot.getKey().toString())){
                        position = i;
                    }
                }
                items.remove(position);
                itemRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class RecentButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(ShoppingList.this, RecentlyPurchased.class);
            ShoppingList.this.startActivity(intent);

        }
    }

    private void setupShoppingListRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(itemRecyclerViewAdapter);

    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, final int direction) {

            switch(direction){
                case ItemTouchHelper.LEFT:
                    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //yes
                                    deleteItem(viewHolder,direction);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No
                                    itemRecyclerViewAdapter.notifyDataSetChanged();
                                    break;

                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogListener).setNegativeButton("No", dialogListener).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    purchaseDialog(items.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                    itemRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }

        }

        public void deleteItem(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
            String id = items.get(viewHolder.getAdapterPosition()).getId();
            db.child(id).removeValue();
        }

        @Override
        public int convertToAbsoluteDirection(int flags, int layoutDirection) {
            return super.convertToAbsoluteDirection(flags, layoutDirection);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX/3, dY/5, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(ShoppingList.this,R.color.design_default_color_error))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(ShoppingList.this, R.color.colorPrimaryDark))
                    .addSwipeRightActionIcon(R.drawable.ic_check_box_white_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX/3, dY/5, actionState, isCurrentlyActive);

        }
    };


    public void purchaseDialog(Item item, int position){
        PurchasedItemDialog purchasedItemDialog = new PurchasedItemDialog(item, position);
        purchasedItemDialog.show(getSupportFragmentManager(),"Item Purchased");
    }

    @Override
    public void purchase(Item item, int position){
        db.child(item.getId()).removeValue();
    }


    public void openDialog(){
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "Add Item");
    }

    @Override
    public void addItem(String item) {

        Item newItem = new Item();
        newItem.setItemName(item);
        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        newItem.setDate(date);
        db.child(String.valueOf(maxId+1)).setValue(newItem);
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
