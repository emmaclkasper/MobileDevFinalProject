package edu.uga.cs.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PurchasedItemDialog extends AppCompatDialogFragment {
    EditText roomatePurchaser,price;
    Item item;
    int position;
    DatabaseReference db;
    long maxId = 0;
    private PurchasedItemDialog.PurchasedItemDialogListener purchasedItemDialogListener;

    public PurchasedItemDialog(Item item, int position) {
        this.item = item;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.purchased_dialog,null);
        roomatePurchaser = view.findViewById(R.id.roomatePurchaser);
        price = view.findViewById(R.id.price);
        db = FirebaseDatabase.getInstance().getReference().child("Purchased");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxId = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        builder.setView(view)
                .setTitle("Purchased Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Purchased", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String purchaser = roomatePurchaser.getText().toString();
                        String purchasedPrice = price.getText().toString();
                        Item newItem = new Item(item.getItemName(),purchasedPrice,purchaser);
                        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        newItem.setDate(date);
                        db.child(String.valueOf(maxId+1)).setValue(newItem);
                        purchasedItemDialogListener.purchase(item, position);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            purchasedItemDialogListener = (PurchasedItemDialog.PurchasedItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface PurchasedItemDialogListener{
        void purchase(Item item, int position);
    }

}
