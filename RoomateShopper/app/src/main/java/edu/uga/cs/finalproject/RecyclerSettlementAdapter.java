package edu.uga.cs.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerSettlementAdapter extends  RecyclerView.Adapter<RecyclerSettlementAdapter.SettlementHolder>{

    private ArrayList<PreviousCosts> previousCostsLists = new ArrayList<>();
    private Context context;
    private SettlementListener settlementListener;

    public RecyclerSettlementAdapter(Context context,ArrayList<PreviousCosts> previousCostsLists, SettlementListener settlementListener ) {
        this.settlementListener = settlementListener;
        this.previousCostsLists = previousCostsLists;
        this.context = context;
    }

    public class SettlementHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView settlementDate,settlementID;
        CardView cardView;
        SettlementListener settlementListener;

        public SettlementHolder(View view, SettlementListener settlementListener){
            super(view);
            this.settlementListener = settlementListener;
            settlementDate = view.findViewById(R.id.settlementDate);
            settlementID = view.findViewById(R.id.settlementID);
            cardView = view.findViewById(R.id.card_view_settlements);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            settlementListener.onSettlementClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public SettlementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_previous_settlements_list,parent,false);
        SettlementHolder holder = new SettlementHolder(view, this.settlementListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettlementHolder holder, int position) {
        //previous costs should be renamed to previous settlements/ settlements
        PreviousCosts previousCosts = previousCostsLists.get(position);

        holder.settlementID.setText(previousCosts.getId());
        holder.settlementDate.setText(previousCosts.getDate());


    }

    @Override
    public int getItemCount() {
        return previousCostsLists.size();
    }

    public interface SettlementListener{
        void onSettlementClick(int position);
    }


}
