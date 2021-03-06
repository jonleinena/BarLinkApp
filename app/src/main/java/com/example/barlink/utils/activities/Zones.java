package com.example.barlink.utils.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;
import com.example.barlink.establishment.Table;
import com.example.barlink.establishment.Zone;
import com.example.barlink.utils.adapters.ZoneAdapter;
import com.example.barlink.utils.sorting.Sorting;

import java.util.ArrayList;

public class Zones extends AppCompatActivity {
    private int idUser;
    private DBManager dbManager;
    private ZoneAdapter adapter;
    private ArrayList<Zone> zoneList;
    private ArrayList<Table> tables;
    private RecyclerView recyclerView;
    private int newZoneId, capacityNum;
    private String newZoneName;
    private Button button;
    private ImageButton imageButton;
    private EditText idET, zoneNameET, capacityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zones);
        getUser();
        dbManager = DBManager.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.zone_recyclerview);
        zoneList = new ArrayList<>();
        zoneList = dbManager.getZones();

        createAdapter();


        idET = (EditText) findViewById(R.id.zoneIdInput);
        zoneNameET = (EditText) findViewById(R.id.zoneNameInput);
        capacityET = (EditText)findViewById(R.id.capacityInput);
        button = (Button) findViewById(R.id.button2);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        tables = new ArrayList<Table>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newZoneName = String.valueOf(zoneNameET.getText()+"");
                newZoneId = Integer.valueOf(idET.getText()+"");
                capacityNum = Integer.valueOf(capacityET.getText()+"");
                Zone zone = new Zone(newZoneId, newZoneName,capacityNum);
                zoneList.add(zone);
                dbManager.saveZone(zone);
                adapter.notifyItemInserted(zoneList.size()-1);

                createTables(capacityNum);
                tables.stream().forEach(table -> dbManager.saveTables(table));
                emptyTextFields();
                showToast("Zone "+ newZoneId + " successfully registered");

            }
        });
        imageButton.setOnClickListener(v -> {
            zoneList = Sorting.mergeSortStr(zoneList);
            createAdapter();
        });
    }
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void createAdapter(){
        // Create adapter passing in the sample user data
        adapter = new ZoneAdapter(zoneList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        adapter.setOnItemClickListener(new ZoneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               Zone selectedZone =  zoneList.get(position);
               openNextActivity(idUser, selectedZone.getIdZone());

            }
        });

    }

    /**
     * Method to get the user who is now using the app
     */
    public void getUser(){
        Intent intent = getIntent();
        idUser = intent.getIntExtra("selectedUser", 0);

    }


    public void emptyTextFields(){
        idET.setText("");
        zoneNameET.setText("");
        capacityET.setText("");
    }

    public void openNextActivity(int idUser, int idZone){
        Intent intent = new Intent(this, Tables.class);
        intent.putExtra("selectedUser", idUser);
        intent.putExtra("selectedZone", idZone);
        startActivity(intent);
    }

    public void createTables(int capacity){
        for(int i = 0; i<capacity; i++){
            this.tables.add(new Table(i, newZoneId));
        }

    }

}