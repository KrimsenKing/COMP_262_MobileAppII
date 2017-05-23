package com.example.scherr3143.websiteimagegui;

import android.content.ComponentName;
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

    public void sendURL(View view) {

        url = String.valueOf(findViewById(R.id.editText));
        Intent urlIntent = new Intent();

        urlIntent.setAction(Intent.ACTION_SEND);
        String pkg = "com.example.scherr3143.contentprovider_downloadservice";
        String cls = "com.example.scherr3143.contentprovider_downloadservice.DownloadService";
        urlIntent.setComponent(new ComponentName(pkg,cls));
        urlIntent.putExtra("url",url);
        startActivity(urlIntent);

    }
}
