package com.example.scherr3143.midtermattemptone;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.content.ContentValues;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by scherr3143 on 5/23/2017.
 */
public class downloadService extends IntentService {

    urlContentProvider cp = new urlContentProvider();
    String stURL;
    public downloadService(String name){super(name);}
    public downloadService() {super("MainActivity");}

    ContentValues cv = new ContentValues();
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent resultIntent = new Intent();
        try {
            //download file, make call to parsing
            stURL = intent.getStringExtra("URL");
            DownloadFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        boolean bolEmpty = cp.checkEmpty(cp.theSitesDB,"urlImages");
        resultIntent.putExtra("Empty",bolEmpty);
        //setResult(1,Activity.RESULT_OK,resultIntent);
        resultIntent.addCategory(Intent.CATEGORY_DEFAULT);
        resultIntent.setAction(MainActivity.responseReceiver.ACTION_RESP);
*/
    }

    public void onDestroy(){

    }

    private String DownloadFile() {

        StringBuffer buffer = new StringBuffer();
        try {
            Document doc = Jsoup.connect(stURL).get();
            // Get document (HTML page) title
            String title = doc.title();
            buffer.append("Title: " + title);
            //buffer.append("Title: " + title + "\r\n");

            Uri uriURL = Uri.parse("content://com.example.scherr3143.midtermattemptone.urlContentProvider/sites");
            cv.put("type", "Site");
            cv.put("param1", stURL);
            cv.put("title", String.valueOf(buffer));
            getContentResolver().insert(uriURL,cv);
            //cp.insert(uriURL, cv);

            Elements media = doc.select("img[src]");
            buffer.append("Image list\r\n");
            for (Element img : media) {
                String src = media.attr("src");

                buffer.append("img URL [" + src + "]");
                //buffer.append("img URL [" + src + "] \r\n");
                cv.put("type", "Image");
                cv.put("param1", String.valueOf(buffer));
                cv.getAsString(src);
                cp.insert(uriURL, cv);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return buffer.toString();
    }
}
