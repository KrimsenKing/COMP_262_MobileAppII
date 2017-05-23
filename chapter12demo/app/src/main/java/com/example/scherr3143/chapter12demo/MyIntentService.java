package com.example.scherr3143.chapter12demo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by scherr3143 on 4/13/2017.
 */
public class MyIntentService extends IntentService {
    private Thread thread = new Thread();
    public MyIntentService() {
        super("MyIntentServiceName");
    }

    protected void onHandleIntent(Intent intent){
        thread.start();
        try{
            int result = DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            Log.d("IntentService", "Download " + result + " bytes");
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    private int DownloadFile(URL url){
        try{
            //Simulate time taken to download
            thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return 100;
    }
}
