package com.example.scherr3143.asynctaskexplore;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button downloadButton;
    private ProgressBar downloadProgressBar;
    private TextView downloadProgressTextView;
    private TextView callBackDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        downloadButton = (Button) findViewById(R.id.button1);
        downloadProgressTextView = (TextView) findViewById(R.id.textView2);
        callBackDisplayTextView = (TextView) findViewById(R.id.textView3);

    }

    public void clearDisplay(View view){
        callBackDisplayTextView.setText(" ");
        downloadButton.setEnabled(true);
    }

    public void startDownload(View view){
        downloadButton.setEnabled(false);
        new PerformAsyncTask().execute();
    }

    private class PerformAsyncTask extends AsyncTask<Void, Integer, Void> {
        int progress_status;

        @Override
        protected Void doInBackground(Void... params) {
            while(progress_status < 100){
                progress_status+=2;
                publishProgress(progress_status);
                SystemClock.sleep(300);
            }
            return null;
        }

        protected void onPreExecute(){
            super.onPreExecute();

            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\n\nLock the screen orientation()");
            int currentOrientation = getResources().getConfiguration().orientation;
            if(currentOrientation == Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nInvoke PreExecure()");
            progress_status = 0;
            downloadProgressTextView.setText("downloading 0%");

            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nCompleted onPreExecute()");
            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nInvoke doInBackground()");
            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nPerforming background work...");

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nCompleted background work");
            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nInvoke onPostExecute()");

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            downloadProgressBar.setProgress(values[0]);
            downloadProgressTextView.setText("downloading " + values[0] + "%");
        }


    }
}
