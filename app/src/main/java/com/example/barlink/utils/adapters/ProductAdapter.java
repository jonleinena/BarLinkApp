package com.example.barlink.utils.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barlink.R;
import com.example.barlink.establishment.Table;
import com.example.barlink.products.Product;

import java.util.ArrayList;


/**
 * @version ALPHA
 * not finished adapter
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ArrayList<Product> myList;
    private Context context;
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
        public TextView productName;
        public TextView price;
        public ImageView img;


        public ViewHolder(View itemView, final ProductAdapter.OnItemClickListener listener) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            price = (TextView)itemView.findViewById(R.id.productPrice);
            img = (ImageView)itemView.findViewById(R.id.productImage);



            itemView.setOnClickListener(v -> {
                if(listener!= null){
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    //constructor
    public ProductAdapter(ArrayList<Product> myList, Context context) {
        this.myList = myList;
        this.context = context;
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

        viewHolder.productName.setText(p.getName()+"");
        viewHolder.price.setText(p.getPrice()+"");
        //viewHolder.img.setImageIcon();

    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

}
