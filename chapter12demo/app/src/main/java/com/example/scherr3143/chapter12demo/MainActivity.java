package com.example.scherr3143.chapter12demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view){
        //startService(new Intent(getBaseContext(),myservice.class));
        //startService(new Intent("net.learn2develop.myservice"));
        startService(new Intent(getBaseContext(), MyIntentService.class));
    }
    public void stopService(View view){
        stopService(new Intent(MainActivity.this, MyIntentService.class));
    }
}
