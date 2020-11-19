package com.example.barlink.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.barlink.R;
import com.example.barlink.command.User;

public class Tables extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        Intent intent = getIntent();
        int u = intent.getIntExtra("selectedUser", 0);
        Log.w("tables", "user id: "+ u);
    }
}