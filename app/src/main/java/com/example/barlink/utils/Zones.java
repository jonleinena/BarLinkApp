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
import com.example.barlink.command.User;
import com.example.barlink.database.DBManager;
import com.example.barlink.establishment.Table;
import com.example.barlink.establishment.Zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Zones extends AppCompatActivity {
    private User user;
    private DBManager dbManager;
    private ZoneAdapter adapter;
    private ArrayList<Zone> zoneList;
    private RecyclerView recyclerView;
    private int newZoneId, newZoneCapacity;
    private Button button;
    private EditText idET, capacityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zones);
        dbManager = DBManager.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.zone_recyclerview);
        zoneList = new ArrayList<>();
        zoneList = getZone(dbManager.getTables(), dbManager.getZoneCapacity());

        createAdapter();


        idET = (EditText) findViewById(R.id.zoneIdInput);
        capacityET = (EditText) findViewById(R.id.capacityInput);
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newZoneCapacity = Integer.valueOf(capacityET.getText()+"");
                newZoneId = Integer.valueOf(capacityET.getText()+"");
                Zone zone = new Zone(newZoneId, newZoneCapacity);
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
             //   User selectedUser =  zoneList.get(position);
               // openNextActivity(selectedUser);

            }
        });

    }

    /**
     * Method to get the user who is now using the app
     */
    public void getUser(){
        Intent intent = getIntent();
        int u = intent.getIntExtra("selectedUser", 0);
        user = dbManager.selectUser(u);
    }

    /**
     * Method to gather the list of zones
     * @param tables ArrayList of all tables in the database
     * @param capacities HashMap with idZone as key and an empty Table array to fill with the tables in the ArrayList
     * @return ArrayList with Zone objects
     */
    public ArrayList<Zone> getZone(ArrayList<Table> tables, HashMap<Integer, Table[]> capacities){
        ArrayList<Zone> zonesList = new ArrayList<>();
        if(tables!=null){
            //First we iterate over the tables arraylist
            for (Table t:tables) {
                //Second, we loop through the Table[] array, to insert the tables to the array of each zone
                for (int i = 0; i<capacities.get((Integer)t.getIdTable()).length; i++){
                    if(capacities.get((Integer)t.getIdTable())[i]==null){
                        capacities.get((Integer)t.getIdTable())[i] = t;
                    }
                }
            }
            Iterator it = capacities.entrySet().iterator();
            //Lastly, we iterate through the hashmap, getting the id, the tables array and the length of it to create adn add zone objects to the zone arraylist
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                zonesList.add(new Zone((int)pair.getKey(),(Table[]) pair.getValue(),((Table[]) pair.getValue()).length));
                it.remove(); // avoids a ConcurrentModificationException
            }
        }

        return zonesList;
    }

    public void emptyTextFields(){
        idET.setText("");
        capacityET.setText("");
    }




}