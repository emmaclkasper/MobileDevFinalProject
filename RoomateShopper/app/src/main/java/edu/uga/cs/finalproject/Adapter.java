package edu.uga.cs.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private LayoutInflater layoutInflater;
    ArrayList<Details> data;
    public static final String DEBUG_TAG = "Adapter";


    Adapter(Context context, ArrayList<Details> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(DEBUG_TAG, "on create ");

        View view = layoutInflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Log.d(DEBUG_TAG, "on bind view ");
        holder.itemName.setText("Item: " + data.get(position).getItemName());
        holder.date.setText("Purchased at " + data.get(position).getDate());
        holder.roommateName.setText("Roommate: " +data.get(position).getRoommateName());
        double priceDub = Double.valueOf(data.get(position).getPrice());
        holder.price.setText("Purchase price: $" + String.format("%.2f",priceDub));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView itemName, date, roommateName, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d( DEBUG_TAG, "on click listener ");
                    Intent i = new Intent(v.getContext(),Details.class);
                    v.getContext().startActivity(i);
                }
            });
            */

            itemName = itemView.findViewById(R.id.item);
            date = itemView.findViewById(R.id.date);
            roommateName = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
