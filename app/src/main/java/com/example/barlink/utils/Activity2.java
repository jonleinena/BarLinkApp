package com.example.barlink.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.barlink.R;
import com.example.barlink.command.User;
import com.example.barlink.database.DBManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User> users;
    private Spinner userTypeMenu;
    private EditText nameInput2;
    private EditText idInput;
    private Button button;
    private String name, selectedItem;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        recyclerView = (RecyclerView) findViewById(R.id.users_reciclerview);
        userTypeMenu = (Spinner)findViewById(R.id.spinner);
        users = new ArrayList<>();
        users = DBManager.readUsers();
        String[] userTypes = new String[3];
        userTypes[0] = "Waiter";
        userTypes[1] = "Chef";
        userTypes[2] = "Maitre";

        nameInput2 = (EditText) findViewById(R.id.nameInput2);
        idInput = (EditText) findViewById(R.id.idInput);
        button = (Button) findViewById(R.id.button);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        userTypeMenu.setAdapter(adapter);
        userTypeMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput2.getText().toString();
                userId = Integer.valueOf(idInput.getText().toString());
                User user = new User(name, userId, selectedItem);
                users.add(user);
            }
        });
    }

    class UsersAdapter extends RecyclerView.Adapter<UserViewHolder>{
        private ArrayList<User> users;
        public UsersAdapter(ArrayList<User> users) {
            super();
            this.users = users;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            holder.bind(this.users.get(position));
        }

        @Override
        public int getItemCount() {
            return this.users.size();
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private TextView userType;
        private TextView userId;

        public UserViewHolder(ViewGroup container) {
            super(LayoutInflater.from(Activity2.this).inflate(R.layout.user_list_item, container));
            username = (TextView)itemView.findViewById(R.id.username);
            userType = (TextView)itemView.findViewById(R.id.userType);
            userId = (TextView)itemView.findViewById(R.id.userId);
        }
        public void bind (User u){
            username.setText(u.getName());
            userType.setText(u.getTypeEmployee());
            userId.setText(u.getIdEmployee());
        }
    }
}

