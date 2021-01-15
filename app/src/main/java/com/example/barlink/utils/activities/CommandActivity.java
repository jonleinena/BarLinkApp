package com.example.barlink.utils.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ExpandableListAdapter;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;
import com.example.barlink.products.Category;
import com.example.barlink.products.Product;
import com.example.barlink.utils.adapters.ExpandableCategoriesAdapter;
import com.example.barlink.utils.adapters.ProductAdapter;
import com.example.barlink.utils.adapters.TablesAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * not finished/working fully
 */
public class CommandActivity extends AppCompatActivity {
    private ExpandableCategoriesAdapter expandableAdapter;
    private DBManager dbManager;
   // private ProductAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6;
    private RecyclerView parentRecyclerview, childRecyclerview1, childRecyclerview2, childRecyclerview3, childRecyclerview4, childRecyclerview5, childRecyclerview6;
    private ArrayList<Category> categories;
    private ArrayList<Product> prods;

    private Product prod;
    private HashMap<Integer, ArrayList<Product>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        dbManager = DBManager.getInstance(this);
        categories = dbManager.getCategories();
       // prods = dbManager.getProducts();
        map = new HashMap<Integer, ArrayList<Product>>();
        parentRecyclerview = (RecyclerView)findViewById(R.id.commandRecyclerView);




        //We create a hashmap containing all category-product relations
        categories.stream().forEach(category -> {
            map.put((Integer)category.getIdCategory(), dbManager.getProducts(category));
        });

        //Here we instantiate the adapters and recyclerviews for the categories and products
        createParentAdapter();

    }





    public void createParentAdapter() {
        // Create adapter passing in the sample user data
        expandableAdapter = new ExpandableCategoriesAdapter(categories, this);
        // Attach the adapter to the recyclerview to populate items
        parentRecyclerview.setAdapter(expandableAdapter);
        // Set layout manager to position the items
        parentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        expandableAdapter.setOnItemClickListener(position -> {
            if(categories.get(position).isExpanded()) categories.get(position).setExpanded(false);
            else categories.get(position).setExpanded(true);
            Log.w("HERE", "clicked title!");
        });
    }







}