package com.example.barlink.utils.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;

public class WelcomeActivity extends AppCompatActivity {
    private Button button;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        dbManager = DBManager.getInstance(this);
        button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // DBManager.endConnection();
    }

    public void openNextActivity(){

        if(dbManager.checkEstablishment()){
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
        } else{
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }
}