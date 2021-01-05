package com.example.barlink.utils.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;
import com.example.barlink.establishment.Establishment;

public class Register extends AppCompatActivity {
    private DBManager dbManager;
    EditText nameInput;
    EditText addressInput;
    EditText nifInput;
    EditText phoneInput;
    EditText emailInput;
    String name, address, nif, email;
    int phone;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Get instance of the database and initialize all the text fields and buttons
        dbManager = DBManager.getInstance(this);
        nameInput = (EditText) findViewById(R.id.nameInput);
        addressInput = (EditText) findViewById(R.id.addressInput);
        nifInput = (EditText) findViewById(R.id.nifInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        button = (Button) findViewById(R.id.submitButton);
        /**
         * When the button is clicked, the establishment instance created eith the textflieds data is saved in the database and the next activity opens.
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the establishment instance out of the text fields' data
                name = nameInput.getText().toString();
                address = addressInput.getText().toString();
                nif = nifInput.getText().toString();
                email = emailInput.getText().toString();
                phone = Integer.valueOf(phoneInput.getText().toString());

                showToast("Successful registration");
                Establishment est = new Establishment(name, address, phone, nif, email);
                //Save establishment in the database
                dbManager.saveEstablishment(est);
                //Open next activity
                openActivity2();
            }
        });
    }

    /**
     * Method to show a simple toast
     * @param text String text to show on the toast
     */
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to open the next activity
     */
    private void openActivity2(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}