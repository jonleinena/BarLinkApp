package com.example.barlink.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barlink.R;
import com.example.barlink.command.User;

/**
 * this adapter is very similar to the adapters used for listview, except a ViewHolder is required
 * see http://developer.android.com/training/improving-layouts/smooth-scrolling.html
 * except instead having to implement a ViewHolder, it is implemented within the adapter.
 */

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.ViewHolder> {
    private List<User> myList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userId;
        public TextView userType;
        public ImageView Pic;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.username);
            userId = (TextView) itemView.findViewById(R.id.userId);
            userType = (TextView) itemView.findViewById(R.id.userType);
            Pic = (ImageView) itemView.findViewById(R.id.image);

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
    public FirstAdapter(List<User> myList) {
        this.myList = myList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View matchesView = inflater.inflate(R.layout.user_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(matchesView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // Get the data model based on position
        User user = myList.get(i);

        // Set item views based on your views and data model
        TextView textView1 = viewHolder.userName;
        textView1.setText(user.getName().toString());
        TextView textView2 = viewHolder.userId;
        textView2.setText(""+user.getIdEmployee());
        TextView textView3 = viewHolder.userType;
        textView3.setText(user.getTypeEmployee().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }
}
