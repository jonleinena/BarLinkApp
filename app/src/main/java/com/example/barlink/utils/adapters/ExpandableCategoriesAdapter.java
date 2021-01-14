package com.example.barlink.utils.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barlink.R;

import com.example.barlink.products.Category;
import com.example.barlink.products.Product;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


/**
 * NOT WORKING
 * @version alpha
 */
public class ExpandableCategoriesAdapter extends RecyclerView.Adapter<ExpandableCategoriesAdapter.ViewHolder> {
    private List<Category> myList;
    private Context context;
    private ExpandableCategoriesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public ConstraintLayout expandableLayout;
        public RecyclerView childRecyclerview;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            expandableLayout = (ConstraintLayout) itemView.findViewById(R.id.expandableLayout);
            categoryName = (TextView) itemView.findViewById(R.id.category);
            childRecyclerview = (RecyclerView) itemView.findViewById(R.id.prod_recyclerview);


            categoryName.setOnClickListener(v -> {

                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                        Category c = myList.get(getAdapterPosition());
                        c.setExpanded(!c.isExpanded());
                        notifyItemChanged(getAdapterPosition());
                        Log.w("this", "you clicked the title!");
                    }
                }


            });
        }
    }

    //constructor
    public ExpandableCategoriesAdapter(List<Category> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpandableCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View matchesView = inflater.inflate(R.layout.category, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(matchesView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExpandableCategoriesAdapter.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        Category c = myList.get(i);

        // Set item views based on your views and data model
        viewHolder.categoryName.setText(c.getName());
        setCatItemRecycler(viewHolder.childRecyclerview, c.getProducts());


        boolean isExpanded = c.isExpanded();

        viewHolder.expandableLayout.setVisibility(!isExpanded ? View.VISIBLE : View.GONE);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

    private void setCatItemRecycler(RecyclerView recyclerView, ArrayList<Product> categoryItemList){

        ProductAdapter itemRecyclerAdapter = new ProductAdapter( categoryItemList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(itemRecyclerAdapter);

    }

}
