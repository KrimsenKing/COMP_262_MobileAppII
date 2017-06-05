package com.example.scherr3143.midtermattemptone;


import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    //private responseReceiver receiver;
    String urlString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //IntentFilter filter = new IntentFilter(responseReceiver.ACTION_RESP);
        //filter.addCategory(Intent.CATEGORY_DEFAULT);
        //receiver = new responseReceiver();
        //registerReceiver(receiver,filter);
    }

    public void sendURL(View view){
        EditText url = (EditText) findViewById(R.id.editText);
        urlString = url.getText().toString();

        Intent urlIntent = new Intent(this,downloadService.class);
        String pkg = "com.example.scherr3143.midtermattemptone";
        String cls = "com.example.scherr3143.midtermattemptone.downloadService";
        urlIntent.setComponent(new ComponentName(pkg, cls));
        urlIntent.putExtra("URL",urlString);
        //int STATIC_INTEGER = 1;
        startService(urlIntent);
        //startActivityForResult(urlIntent,STATIC_INTEGER);
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent intent){
 //       // Check which request we're responding to
  //      if (requestCode == 1) {
   //         // Make sure the request was successful
    //        if (resultCode == RESULT_OK) {
    //            // The response returned images
     //     }
      //  }
    //}

   /* public class responseReceiver extends BroadcastReceiver{

        public static final String ACTION_RESP = "com.example.scherr3143.midtermattemptone.MESSAGE_PROCESSED";
        public void onReceive(Context context, Intent intent){
            TextView images = (TextView) findViewById(R.id.scrollView);
            String textImages = intent.getStringExtra("Empty");
            images.setText(textImages);
        }
    }*/
}
