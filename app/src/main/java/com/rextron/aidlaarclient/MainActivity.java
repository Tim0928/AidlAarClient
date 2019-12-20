package com.rextron.aidlaarclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.rextron.aidlaarserver.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate");
        initial();
    }
    void initial() {
        Intent intent = new Intent();
        Log.e(TAG, "initial");
        //intent.setPackage(IMyAidlInterface.class.getPackage().getName());
        intent.setClassName(IMyAidlInterface.class.getPackage().getName(),"com.rextron.aidlaarserver.MyService");

        Log.e(TAG, "initial2"+IMyAidlInterface.class.getPackage().getName());
        boolean ret=bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.e(TAG, "initial3:"+ret);
    }
    IMyAidlInterface mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            Log.e(TAG, "className:"+className);
            mService = IMyAidlInterface.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected2");

            try {
                mService.getAge();
                Log.e(TAG, "AGE:"+mService.getAge());
            } catch (RemoteException e) {
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "onServiceDisconnected");
            mService = null;

        }
    };
}
