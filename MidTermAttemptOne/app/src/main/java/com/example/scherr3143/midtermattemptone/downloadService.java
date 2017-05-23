package com.example.scherr3143.midtermattemptone;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.content.ContentValues;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by scherr3143 on 5/23/2017.
 */
public class downloadService extends IntentService {

    ContentValues cv = new ContentValues();
    urlContentProvider cp = new urlContentProvider();
    public downloadService(String name){super(name);}
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            //download file, make call to parsing
            DownloadFile(intent.getStringExtra("URL"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy(){
        Intent resultIntent = new Intent();

        boolean bolEmpty = cp.checkEmpty(cp.theSitesDB,"urlImages");
        resultIntent.putExtra("Empty",bolEmpty);

        setResult(1,Activity.RESULT_OK,resultIntent);
        finish();
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
    }
}
