package com.example.barlink.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barlink.R;
import com.example.barlink.establishment.Table;

import java.util.ArrayList;

public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.ViewHolder>{

    private ArrayList<Table> myList;
    private TablesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(TablesAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tablesItemName;


        public ViewHolder(View itemView, final TablesAdapter.OnItemClickListener listener) {
            super(itemView);
            tablesItemName = (TextView) itemView.findViewById(R.id.tableNum);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!= null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    //constructor
    public TablesAdapter(ArrayList<Table> myList) {
        this.myList = myList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View matchesView = inflater.inflate(R.layout.table_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(matchesView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // Get the data model based on position
        Table table = myList.get(i);


        // Set item views based on your views and data model
        TextView tableNum = viewHolder.tablesItemName;
        tableNum.setText(table.getIdTable()+"");

    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }
}
