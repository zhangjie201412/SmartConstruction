package com.jay.iot.smartconstruction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jay.iot.smartconstruction.comm.SocketClientTest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button mHelloButton;

    private ISmartSocket mSocketManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mSocketManager = ISmartSocket.Stub.asInterface(iBinder);
            try {
                mSocketManager.connectServer();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSocketManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloButton = (Button)findViewById(R.id.bt_hello);
        mHelloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
                try {
                    mSocketManager.sendPowerStatus("SN02201708210001", 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent = new Intent("com.jay.iot.smartconstruction.service.SmartSocketService");
        intent.setPackage("com.jay.iot.smartconstruction");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
