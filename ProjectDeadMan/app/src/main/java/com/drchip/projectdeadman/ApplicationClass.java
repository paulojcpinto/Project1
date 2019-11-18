package com.drchip.projectdeadman;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

public class ApplicationClass extends Application {

    public static int connectedTo;
    public static BluetoothAdapter BA;

    public void onCreate() {
        super.onCreate();
    }

}
