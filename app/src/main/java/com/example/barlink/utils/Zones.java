package com.example.barlink.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;
import com.example.barlink.establishment.Zone;
import com.example.barlink.utils.adapters.ZoneAdapter;

import java.util.ArrayList;

public class Zones extends AppCompatActivity {
    private int idUser;
    private DBManager dbManager;
    private ZoneAdapter adapter;
    private ArrayList<Zone> zoneList;
    private RecyclerView recyclerView;
    private int newZoneId;
    private String newZoneName;
    private Button button;
    private EditText idET, zoneNameET;

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
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newZoneName = String.valueOf(zoneNameET.getText()+"");
                newZoneId = Integer.valueOf(idET.getText()+"");
                Zone zone = new Zone(newZoneId, newZoneName);
                zoneList.add(zone);
                dbManager.saveZone(zone);
                adapter.notifyItemInserted(zoneList.size()-1);
                emptyTextFields();
                showToast("Zone "+ newZoneId + " successfully registered");

            }
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
    }

    public void openNextActivity(int idUser, int idZone){
        Intent intent = new Intent(this, Tables.class);
        intent.putExtra("selectedUser", idUser);
        intent.putExtra("selectedZone", idZone);
        startActivity(intent);
    }


}