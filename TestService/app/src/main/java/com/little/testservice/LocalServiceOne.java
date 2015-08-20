package com.little.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import  android.os.Binder;
import android.util.Log;

public class LocalServiceOne extends Service {

    IBinder binder = new LocalServiceOneBinder();

    public LocalServiceOne() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        ActivityMain.tv.append("\r\nonBind");
        return binder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        ActivityMain.tv.append("\r\nonUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityMain.tv.append("\r\nonCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ActivityMain.tv.append("\r\nonStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

/*    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        ActivityMain.tv.append("\r\nonStart");
    }*/


    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityMain.tv.append("\r\ndestroy");
    }


    public class LocalServiceOneBinder extends Binder {

    }
}
