package com.example.barlink.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barlink.R;
import com.example.barlink.database.DBManager;
import com.example.barlink.establishment.Establishment;

public class Register extends AppCompatActivity {
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

        nameInput = (EditText) findViewById(R.id.nameInput);
        addressInput = (EditText) findViewById(R.id.addressInput);
        nifInput = (EditText) findViewById(R.id.nifInput);
        phoneInput = (EditText) findViewById(R.id.telephoneInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                address = addressInput.getText().toString();
                nif = nifInput.getText().toString();
                email = emailInput.getText().toString();
                phone = Integer.valueOf(phoneInput.getText().toString());

                showToast("Successful registration");
                Establishment est = new Establishment(name, address, phone, nif, email);
                DBManager.InsertEstablishment(est);
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}