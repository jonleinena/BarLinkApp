package com.example.barlink.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barlink.R;
import com.example.barlink.establishment.Table;
import com.example.barlink.products.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ArrayList<Product> myList;
    private ProductAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tablesItemName;


        public ViewHolder(View itemView, final ProductAdapter.OnItemClickListener listener) {
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
    public ProductAdapter(ArrayList<Product> myList) {
        this.myList = myList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View matchesView = inflater.inflate(R.layout.product_element, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(matchesView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        Product p = myList.get(i);


        // Set item views based on your views and data model
        TextView tableNum = viewHolder.tablesItemName;
        tableNum.setText(p.getIdProduct()+"");

    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

}
