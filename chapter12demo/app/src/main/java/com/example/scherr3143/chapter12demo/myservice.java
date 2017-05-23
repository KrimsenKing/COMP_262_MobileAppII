package com.example.scherr3143.chapter12demo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by scherr3143 on 4/10/2017.
 */
public class myservice extends Service {

    int counter=0;
    static final int UPDATE_INTERVAL=1000;
    private Timer timer = new Timer();
    public IBinder onBind(Intent arg0){
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startID){
        //this service should continue to run until it is explicitly stopped, so return sticky.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        doSomethingRepeatedly();
        try{
            //int result = DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            new DoBackgroundTask().execute(
                    new URL("http://www.amazon.com/somefile.pdf"),
                    new URL("http://www.wrox.com/somefile.pdf"),
                    new URL("http://www.google.com/somefile.pdf"),
                    new URL("http://www.learn2develop.com/somefile.pdf"));
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        return START_STICKY;
    }

    private void doSomethingRepeatedly(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d("MyService", String.valueOf(++counter));
            }
        },0,UPDATE_INTERVAL);
    }
    private int DownloadFile(URL url){
        try{
            //Simulating time take to complete a download
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        //return number representing the size of download
        return 100;
    }

    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                //Calculate the download progress and return the percentage
                publishProgress((int) (((i + 1) / (float) count) * 100));
            }
            return totalBytesDownloaded;
        }

        protected void onProgressUpdate(Integer... progress){
            Log.d("Download files", String.valueOf(progress[0])+"% downloaded");
            Toast.makeText(getBaseContext(), String.valueOf(progress[0])+"% downloaded",Toast.LENGTH_LONG).show();
        }

        protected void onPostExecute(Long result) {
            Toast.makeText(getBaseContext(), "Download " + result + " bytes", Toast.LENGTH_LONG).show();
            stopSelf();
        }
    }
    public void onDestroy(){
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
    }
}
