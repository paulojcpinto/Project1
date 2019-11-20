package com.drchip.projectdeadman;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

public class Enter extends AppCompatActivity {

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    private static final String TAG = "BluetoothChatService";
    ArrayList<MybluetoothDevice> loadedDevices;
    Button btnLogin, btnRegister, btnSend;
    ImageView ivLogo, ivStatus;
    ArrayList<BluetoothDevice> listb;
    String deviceType;
    String name;
    EditText etSend;
    TextView tvDevice;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    if (readMessage.contains("STM")) {
                        deviceType = "STM";
                        connected = true;
                        ivStatus.setImageResource(R.drawable.done);

                        Toast.makeText(Enter.this, "Connected with sucess", Toast.LENGTH_SHORT).show();
                        loadedDevices.add(new MybluetoothDevice(ApplicationClass.target, deviceType));
                        saveDevice();
                    } else if (readMessage.equals("rasp")) {
                        deviceType = "RASP";
                        connected = true;
                        ivStatus.setImageResource(R.drawable.done);
                        Toast.makeText(Enter.this, "Connected with sucess", Toast.LENGTH_SHORT).show();
                        loadedDevices.add(new MybluetoothDevice(ApplicationClass.target, deviceType));
                        saveDevice();
                    }

                    Toast.makeText(Enter.this, "Receibed " + readMessage, Toast.LENGTH_SHORT).show();
                    break;

                case MESSAGE_TOAST:
                    if (msg.getData().getString(TOAST).contains("Device connection was lost")) {
                        connected = false;
                        ivStatus.setImageResource(R.drawable.error);
                    }
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Set<BluetoothDevice> pairedDevices;
    boolean choosed, connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister= findViewById(R.id.btnRegister);
        tvDevice = findViewById(R.id.tvDevice);
        ivStatus = findViewById(R.id.ivStatus);
        btnSend = findViewById(R.id.btnSend);
        etSend = findViewById(R.id.etSend);
        connected = false;
        listb = new ArrayList<BluetoothDevice>();
        loadedDevices = new ArrayList<>();
        ApplicationClass.mBluetoothConnectionService = new BluetoothConnectionService(this, mHandler);
        ApplicationClass.mBluetoothConnectionService.start();

        name = "";
        ivLogo = findViewById(R.id.ivLogo);

        loadData();

        if (loadedDevices.size() >= 1) {
            tvDevice.setText(loadedDevices.get(loadedDevices.size() - 1).device.getName());

            ApplicationClass.target = loadedDevices.get(loadedDevices.size() - 1).device;
        }

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


        ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!connected) {
                    // Get the BLuetoothDevice object
                    //String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    //
                    BluetoothDevice device = ApplicationClass.BA.getRemoteDevice(ApplicationClass.target.getAddress());
                    if (device != null)
                        Toast.makeText(Enter.this, "YESS", Toast.LENGTH_SHORT).show();
                    // Attempt to connect to the device
                    ApplicationClass.mBluetoothConnectionService.connect(device);


                    for (int i = 0; i < 10000000; i++) for (int j = 0; j < 115; j++) ;
                    ApplicationClass.sendMessage("O", Enter.this);

                    ApplicationClass.sendMessage("<L>", Enter.this);

                } else
                    Toast.makeText(Enter.this, "You are already connected to that device", Toast.LENGTH_SHORT).show();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ApplicationClass.mBluetoothConnectionService.write(bytes);
                ApplicationClass.sendMessage(etSend.getText().toString(), Enter.this);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.enter_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {   // override metodos em code !!!

        switch (item.getItemId()) {
            case R.id.addDevice:

                Toast.makeText(this, "Add Device Clicked", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder message = new AlertDialog.Builder(Enter.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.paired_devices, null);
                //   final EditText etReleaseMessage = dialogView.findViewById(R.id.etReleaseMessage);
                final RadioButton rbSTM = dialogView.findViewById(R.id.rbSTM);
                final RadioButton rbRasp = dialogView.findViewById(R.id.rbRasp);
                final ListView lv = dialogView.findViewById(R.id.lvPairedDevices);

                final TextView tvBname = dialogView.findViewById(R.id.tvBName);
                final TextView tvBmac = dialogView.findViewById(R.id.tvBmac);
                final ImageView ivType = dialogView.findViewById(R.id.ivType);
                final LinearLayout llSelected = dialogView.findViewById(R.id.llSelected);
                choosed = false;
                llSelected.setVisibility(View.GONE);
                pairedDevices = ApplicationClass.BA.getBondedDevices();


                listb.addAll(pairedDevices);
                // final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);


                message.setView(dialogView);
                message.setTitle("Bluetooth Devices");
                message.setMessage("Select the device that you want to add!!");


                rbSTM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyBluetoothAdapter adapter = new MyBluetoothAdapter(getApplicationContext(), listb, 1);

                        choosed = false;
                        lv.setAdapter(adapter);

                        llSelected.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                    }
                });

                rbRasp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyBluetoothAdapter adapter = new MyBluetoothAdapter(getApplicationContext(), listb, 2);

                        choosed = false;
                        lv.setAdapter(adapter);
                        llSelected.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);


                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ApplicationClass.target = listb.get(position);

                        Toast.makeText(Enter.this, "Device added succesfully " + listb.get(position).getName(), Toast.LENGTH_SHORT).show();
                        tvBname.setText(listb.get(position).getName());
                        tvBmac.setText(listb.get(position).getAddress());
                        if (rbSTM.isChecked()) {
                            ivType.setImageResource(R.drawable.stm);
                        } else if (rbRasp.isChecked()) {
                            ivType.setImageResource(R.drawable.rasp);
                        }
                        lv.setVisibility(View.GONE);

                        llSelected.setVisibility(View.VISIBLE);
                        choosed = true;

                    }
                });



                message.setPositiveButton("Comfirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (choosed) {
                            Toast.makeText(Enter.this, "Selected succesfuly", Toast.LENGTH_SHORT).show();
                            tvDevice.setText(ApplicationClass.target.getName());

                        } else {
                            Toast.makeText(Enter.this, "Please select one device", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }

                    }
                });
                message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                message.show();

                break;
            case R.id.SelectDevice:


                break;
//            case  R.id.refresh:
//                Toast.makeText(this, "Refresh Selected", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.send:
//                Toast.makeText(this, "Send Clicked", Toast.LENGTH_SHORT).show();
//                break;


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void saveDevice() {

        try {
            FileOutputStream file = openFileOutput("Data.txt", MODE_PRIVATE);  //cria o ficheiro caso nao exitsa, e define a permisao do ficheiro para so o nossa aplicaçao
            OutputStreamWriter outputFile = new OutputStreamWriter(file);  //cria a connecao com o ficheiro que vamos escrever

            for (int i = 0; i < loadedDevices.size(); i++) {
                outputFile.write(loadedDevices.get(i).device.getName() + "," + loadedDevices.get(i).device.getAddress() + "," + loadedDevices.get(i).deviceType);


            }
            outputFile.flush();
            outputFile.close();
            Toast.makeText(this, "Sucessfully savedes eyeyeyyeyeyeyeyeyeye!", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loadData() {
        loadedDevices.clear();
        File file = getApplicationContext().getFileStreamPath("Data.txt");
        String linefromFile;
        if (file.exists()) {
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));  //abre p ficheiro Data.txt para leitura!
                while ((linefromFile = reader.readLine()) != null) {
                    StringTokenizer tokens = new StringTokenizer(linefromFile, ",");
                    String DeviceName = tokens.nextToken();
                    String DeviceMac = tokens.nextToken();
                    String type = tokens.nextToken();
                    Set<BluetoothDevice> auxDevices = ApplicationClass.BA.getBondedDevices();
                    for (BluetoothDevice test : auxDevices) {
                        if (test.getName().equals(DeviceName)) {
                            if (test.getAddress().equals(DeviceMac)) {
                                loadedDevices.add(new MybluetoothDevice(test, type));
                            }


                        }
                    }

                }

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else

            tvDevice.setText("No device selected!");

    }






}
