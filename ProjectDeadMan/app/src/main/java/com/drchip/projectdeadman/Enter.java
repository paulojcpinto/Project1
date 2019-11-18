package com.drchip.projectdeadman;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Set;

public class Enter extends AppCompatActivity {

    Button btnLogin, btnRegister;
    ImageView ivLogo;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister= findViewById(R.id.btnRegister);
        ApplicationClass.BA = BluetoothAdapter.getDefaultAdapter();

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
                Snackbar.make(v, "Made By:  André Campos \r\n                           Paulo Pinto", Snackbar.LENGTH_SHORT).show();

            }
        });

        if (!ApplicationClass.BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }

        final AlertDialog.Builder message = new AlertDialog.Builder(Enter.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.paired_devices, null);
        //   final EditText etReleaseMessage = dialogView.findViewById(R.id.etReleaseMessage);
        final RadioButton rbSTM = dialogView.findViewById(R.id.rbSTM);
        final RadioButton rbRasp = dialogView.findViewById(R.id.rbRasp);
        final ListView lv = dialogView.findViewById(R.id.lvPairedDevices);

        pairedDevices = ApplicationClass.BA.getBondedDevices();

        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices) list.add(bt.getName());
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);

        message.setView(dialogView);
        message.setTitle("Platform to Release");
        message.setMessage("Enter the platform that you want to release here!!");


        rbSTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lv.setVisibility(View.VISIBLE);
            }
        });

        rbRasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.VISIBLE);


            }
        });

        // message.show();

    }
}
