package com.example.scherr3143.countingthreadexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countTextView;
    private Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference the text view ui element
        countTextView = (TextView) findViewById(R.id.textView);

        //Initialize the counter;
        count=0;

        //Create a thread and start it
        Thread thread = new Thread(countNumbers);
        thread.start();
    }

    protected void onStart(){
        super.onStart();
        count = 0;
    }

    //**Runnable**/
    private Runnable countNumbers = new Runnable() {
        private static final int DELAY = 1000;
        @Override
        public void run() {
            try{
                while(true){
                    count++;
                    Thread.sleep (DELAY);
                    threadHandler.sendEmptyMessage(0);
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    };

    //**Handler**/
    public Handler threadHandler = new Handler(){
        public void handleMessage (android.os.Message message){
            countTextView.setText(count.toString());
        }
    };
}
