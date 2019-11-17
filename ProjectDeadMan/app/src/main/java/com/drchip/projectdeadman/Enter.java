package com.drchip.projectdeadman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class Enter extends AppCompatActivity {

    Button btnLogin, btnRegister;
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister= findViewById(R.id.btnRegister);

        ivLogo = findViewById(R.id.ivLogo);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Enter.this,Register.class));
                Enter.this.finish();

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Enter.this,Login.class));
                Enter.this.finish();

            }
        });
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Made By:  André Campos \r\n                           Paulo Pindo",Snackbar.LENGTH_SHORT).show();

            }
        });

    }
}
