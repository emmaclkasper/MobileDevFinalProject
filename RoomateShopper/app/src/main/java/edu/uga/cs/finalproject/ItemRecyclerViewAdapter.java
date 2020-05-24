package edu.uga.cs.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemHolder> {

    private List<Item> itemList;
    private Context mContext;
    public ItemRecyclerViewAdapter (Context context,List<Item> itemList ){this.itemList = itemList; mContext = context;}

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView itemName, date;
        CardView cardView;

        public ItemHolder(View view){
            super(view);
            itemName = view.findViewById(R.id.itemName);
            date = view.findViewById(R.id.date);
            cardView = view.findViewById(R.id.card_view);
        }

    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list,parent,false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        Item currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getItemName());
        holder.date.setText("Item added on: " + currentItem.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,itemList.get(position).getItemName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



}
