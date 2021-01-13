package com.example.barlink.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barlink.R;

import com.example.barlink.products.Category;
import com.example.barlink.products.Product;

import java.util.List;

public class ExpandableCategoriesAdapter extends RecyclerView.Adapter<ExpandableCategoriesAdapter.ViewHolder>{
    private List<Category> myList;
    private ExpandableCategoriesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ExpandableCategoriesAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public ConstraintLayout expandableLayout;

        public ViewHolder(View itemView, final ExpandableCategoriesAdapter.OnItemClickListener listener) {
            super(itemView);
            expandableLayout = (ConstraintLayout)itemView.findViewById(R.id.expandableLayout);
            categoryName = (TextView) itemView.findViewById(R.id.category);


            categoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category c = myList.get(getAdapterPosition());
                    c.setExpanded(! c.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }

    //constructor
    public ExpandableCategoriesAdapter(List<Category> myList) {
        this.myList = myList;
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

        boolean isExpanded = c.isExpanded();

        viewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }
}
