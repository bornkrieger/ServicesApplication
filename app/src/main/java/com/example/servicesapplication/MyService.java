package com.example.servicesapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class MyService extends Service {

    private MediaPlayer player;
    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;


    class MyServiceBinder extends Binder
    {
        public MyService getService(){
            return MyService.this;
        }

    }

    private IBinder mBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Running", "in onBind: ");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Running", "in onStartCommand: ");

         new Thread(new Runnable() {
             @Override
             public void run() {

                 startRandomNoGenerator();
             }
         }).start();
//        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
//        player.setLooping(true);
//        player.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("Running", "in onDestroy: ");
        stopRandomNoGenerator();
        super.onDestroy();
    }




    private void startRandomNoGenerator()
    {    mIsRandomGeneratorOn = true;

        while(mIsRandomGeneratorOn){

            try {
                Thread.sleep(1000);

                if(mIsRandomGeneratorOn){
                    mRandomNumber = new Random().nextInt(100)+1;
                    Log.i("Running", "startRandomNoGenerator: " + mRandomNumber);
                }

            }catch (Exception e ){
                Log.e("Running", "startRandomNoGenerator error: "+ e.getMessage() );
            }

        }

    }

    private void stopRandomNoGenerator()
    {
        Log.i("Running", "in stopRandomNoGenerator: ");
          mIsRandomGeneratorOn = false;
    }

    public int getmRandomNumber(){ return mRandomNumber; }
}
