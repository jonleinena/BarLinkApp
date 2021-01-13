package com.example.barlink.utils.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.barlink.R;
import com.example.barlink.command.User;
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
    private Table table;
    private TablesAdapter adapter;

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

    public void createAdapter() {
        // Create adapter passing in the sample user data
        adapter = new TablesAdapter(tablesList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        // That's all!
        adapter.setOnItemClickListener(new TablesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                table = tablesList.get(position);


            }
        });

    }


    /**
     * Method to get the user who is now using the app and the zone
     */
    public void getExtra() {
        Intent intent = getIntent();
        idUser = intent.getIntExtra("selectedUser", 0);
        idZone = intent.getIntExtra("selectedZone", 1);

    }

    /**
     * Method to open next activity
     *
     * @param user user selected, needed to pass the active user's id for following activities.
     */
    private void openNextActivity(User user) {
        Intent intent = new Intent(this, CommandActivity.class);
        intent.putExtra("selectedUser", idUser);
        intent.putExtra("selectedZone", idZone);
        intent.putExtra("selectedTable", table.getIdTable());
        startActivity(intent);
    }
}