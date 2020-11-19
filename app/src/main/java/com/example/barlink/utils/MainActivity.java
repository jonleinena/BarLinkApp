package com.example.barlink.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barlink.R;
import com.example.barlink.command.User;
import com.example.barlink.database.DBManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    private RecyclerView recyclerView;
    private ArrayList<User> users;
    private Spinner userTypeMenu;
    private EditText nameInput2;
    private EditText idInput;
    private Button button;
    private String name, selectedItem;
    private int userId;
    private myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        dbManager = DBManager.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.users_reciclerview);
        users = new ArrayList<>();
        users = dbManager.getUsers();
        createAdapter();
        createSpinner();

        nameInput2 = (EditText) findViewById(R.id.nameInput2);
        idInput = (EditText) findViewById(R.id.idInput);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput2.getText().toString();
                userId = Integer.valueOf(idInput.getText().toString());
                User user = new User(name, userId, selectedItem);
                users.add(user);
                dbManager.saveUser(user);
                adapter.notifyItemInserted(users.size()-1);
                emptyTextFields();
                showToast("User "+ name + " successfully registered");
            }
        });

    }

    public void createAdapter(){
        // Create adapter passing in the sample user data
        adapter = new myAdapter(users);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        adapter.setOnItemClickListener(new myAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               User selectedUser =  users.get(position);
                openNextActivity(selectedUser);

            }
        });

    }

    public void createSpinner(){
        userTypeMenu = (Spinner) findViewById(R.id.spinner);
        String[] userTypes = new String[3];
        userTypes[0] = "Waiter";
        userTypes[1] = "Chef";
        userTypes[2] = "Maitre";

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        userTypeMenu.setAdapter(adapter1);
        userTypeMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void emptyTextFields(){
        nameInput2.setText("");
        idInput.setText("");
    }
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void openNextActivity(User user){
        Intent intent = new Intent(this, Tables.class);
        intent.putExtra("selectedUser", user.getIdEmployee());
        startActivity(intent);
    }
}


