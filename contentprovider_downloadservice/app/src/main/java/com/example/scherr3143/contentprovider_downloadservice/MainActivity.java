package com.example.scherr3143.contentprovider_downloadservice;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends IntentService {

    public MainActivity(String name) {
        super(name);
    }

    public MainActivity() {
        super("MainActivity");
    }
    ContentValues cv = new ContentValues();

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            DownloadFile(intent.getStringExtra("URL"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DownloadFile(String url) {
        try {
            //download file, make call to parsing
            (new ParseURL()).execute(new String[]{url});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ParseURL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            urlContentProvider cp = new urlContentProvider();
            StringBuffer buffer = new StringBuffer();

            try {
                Log.d("JSwa", "Connecting to [" + strings[0] + "]");
                Document doc = Jsoup.connect(strings[0]).get();
                Log.d("JSwa", "Connected to [" + strings[0] + "]");
                // Get document (HTML page) title
                String title = doc.title();
                Log.d("JSwA", "Title [" + title + "]");
                buffer.append("Title: " + title + "\r\n");

                cv.put("type", "Site");
                cv.put("param1", strings[0]);
                cv.put("title", String.valueOf(buffer));
                cp.insert(null, cv);

                Elements media = doc.select("img[src]");
                buffer.append("Image list\r\n");
                for (Element img : media) {
                    String src = media.attr("src");

                    buffer.append("img URL [" + src + "] \r\n");
                    cv.put("type", "Image");
                    cv.put("param1", String.valueOf(buffer));
                    cv.getAsString(src);
                    cp.insert(null, cv);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
