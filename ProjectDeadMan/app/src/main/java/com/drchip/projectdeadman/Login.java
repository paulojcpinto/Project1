package com.drchip.projectdeadman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    AutoCompleteTextView etNick;
    Button btnCancel, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);   //todo send signal bluetooth to rasp or stm and see who is connected.

        etNick =  findViewById(R.id.etNick);
        btnCancel = findViewById(R.id.btnCancel);
        btnLogin = findViewById(R.id.btnLogin);

        String [] names = {"James","John", "Jenny","Pila","Jenine","Jack","PauloGay"};  // lista de nomes para aparecer na ajuda

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, R.layout.custom_design_autocomlete,names);

        etNick.setThreshold(1);  //Numero de caraters que o utilizador percisa de por para começar a aparecer a funcao de autocomplete
        etNick.setAdapter(adapter);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo implement bluetooth method
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this,Enter.class));
                Login.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Login.this, Enter.class));
        Login.this.finish();
    }
}
