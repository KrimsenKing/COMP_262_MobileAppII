package com.example.scherr3143.midtermattemptone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendURL(View view){
        url = String.valueOf(findViewById(R.id.editText));
        Intent urlIntent = new Intent();
        urlIntent.putExtra("url",url);
        int STATIC_INTEGER = 1;
        startActivityForResult(urlIntent,STATIC_INTEGER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }
}
