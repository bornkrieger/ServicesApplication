package com.example.servicesapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {





private Button startBtn , endBtn, bindBtn, unbindBtn, getrandomnoBtn;
private TextView textView;
private Intent serviceIntent;
private MyService myService;
private Boolean isServiceBound=false;
private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.start_service);
        endBtn = findViewById(R.id.stop_service);
        bindBtn = findViewById(R.id.bind_service);
        unbindBtn = findViewById(R.id.unbind_service);
        getrandomnoBtn =findViewById(R.id.get_random_number);
        textView = findViewById(R.id.textView);

        serviceIntent = new Intent(MainActivity.this,MyService.class);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(serviceIntent);
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });


        bindBtn.setOnClickListener(this);
        unbindBtn.setOnClickListener(this);
        getrandomnoBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.bind_service: bindService(); break;
            case R.id.unbind_service: unbindService(); break;
            case R.id.get_random_number: getrandomno();break;
            default: break;



        }

    }

    private void getrandomno() {
        //check if service is bound only then set the text view.
        if(isServiceBound){
          textView.setText("Random no:"+myService.getmRandomNumber());
        }else {
            textView.setText("Service not bound");
        }
    }

    private void unbindService() {

        //check if service is bound.
        if(isServiceBound){
            //unbinding service.
            unbindService(serviceConnection);
            isServiceBound =false;
        }
    }

    private void bindService() {

        //checling if service connection is null
        if(serviceConnection==null){
            //if null then initializing it.
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder)service;
                    myService=myServiceBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };
        }

        //binding service after initialization of service connection.
        bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
