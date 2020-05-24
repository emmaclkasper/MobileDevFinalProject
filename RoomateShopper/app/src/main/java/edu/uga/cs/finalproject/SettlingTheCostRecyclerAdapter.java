package edu.uga.cs.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettlingTheCostRecyclerAdapter extends RecyclerView.Adapter<SettlingTheCostRecyclerAdapter.RoommateHolder> {

    private ArrayList<Roomates> roommatesList;
    private Context mContext;
    public SettlingTheCostRecyclerAdapter(Context context, ArrayList<Roomates> roommatesList){
        mContext = context;
        this.roommatesList = roommatesList;
    }

    public class RoommateHolder extends RecyclerView.ViewHolder{
        TextView roommateName, price;
        CardView cardView;

        public RoommateHolder(View view) {
            super(view);
            roommateName = view.findViewById(R.id.roomateName);
            price = view.findViewById(R.id.totalPaid);
            cardView = view.findViewById(R.id.card_view_roomates);
        }
    }


    @NonNull
    @Override
    public RoommateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomates_card_view,parent,false);
        RoommateHolder holder = new RoommateHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoommateHolder holder, final int position) {
        Roomates currentRoommate = roommatesList.get(position);

        holder.roommateName.setText(currentRoommate.getName());
        holder.price.setText("Total amount spent: $" + currentRoommate.getAmountSpent());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,roommatesList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return roommatesList.size();
    }
}
