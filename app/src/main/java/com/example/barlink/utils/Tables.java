package com.example.barlink.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;
import com.example.barlink.establishment.Table;
import com.example.barlink.establishment.Zone;
import com.example.barlink.utils.adapters.GenericAdapter;
import com.example.barlink.utils.adapters.TablesAdapter;
import com.example.barlink.utils.adapters.ZoneAdapter;

import java.util.ArrayList;

public class Tables extends AppCompatActivity {
    private ArrayList<Table> tablesList;
    private RecyclerView recyclerView;
    private DBManager dbManager;
    private int idUser, idZone;
    private GenericAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        dbManager = DBManager.getInstance(this);
        getExtra();
        tablesList = dbManager.getTablesByZone(idZone);
        recyclerView = (RecyclerView) findViewById(R.id.tables_recyclerview);
        createAdapter();





    }

    public void createAdapter(){
        // Create adapter passing in the sample user data
        adapter = new GenericAdapter(this,tablesList) {
            @Override
            public int getLayoutResId() {
                return R.layout.activity_tables;
            }

            @Override
            public void onBindData(Object model, int position, Object dataBinding) {

            }

            @Override
            public void onItemClick(Object model, int position) {

            }
        };
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        /**
        adapter.setOnItemClickListener(new ZoneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Zone selectedZone =  zoneList.get(position);
                openNextActivity(idUser, selectedZone.getIdZone());

            }
        });
         */
    }


    /**
     * Method to get the user who is now using the app and the zone
     */
    public void getExtra(){
        Intent intent = getIntent();
        idUser = intent.getIntExtra("selectedUser", 0);
        idZone = intent.getIntExtra("selctedZone", 1);

    }
}