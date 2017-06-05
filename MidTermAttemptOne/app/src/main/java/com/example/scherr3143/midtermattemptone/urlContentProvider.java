package com.example.scherr3143.midtermattemptone;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by scherr3143 on 5/23/2017.
 */
public class urlContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.scherr3143.midtermattemptone.urlContentProvider";
    static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/sites");
    static final String _siteID = "siteid";
    static final String _siteNAME = "sitename";
    static final String _siteTITLE = "sitetitle";
    static final String _photoID = "photoid";
    static final String _photoNAME = "photoname";

    SQLiteDatabase theSitesDB;
    static final String DATABASE_NAME = "Sites";
    static final String DATABASE_TABLE1 = "urlSites";
    static final String DATABASE_TABLE2 = "urlImages";
    static final int DATABASE_VERSION = 2;

    static final String SITES_TABLE_CREATE = "Create table " + DATABASE_TABLE1 + "(" + _siteID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _siteNAME + " TEXT, " + _siteTITLE + " TEXT);";

    static final String IMAGES_TABLE_CREATE = "Create table " + DATABASE_TABLE2 + "(" + _photoID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _siteID + " INTEGER, " + _photoNAME + " TEXT, FOREIGN KEY (" + _siteID + ") REFERENCES " + DATABASE_TABLE1 + "(" + _siteID + "));";


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){super(context,DATABASE_NAME,null,DATABASE_VERSION);}
        @Override
        public void onCreate(SQLiteDatabase db) {
            //create the tables whn the database is first created
            db.execSQL(SITES_TABLE_CREATE);
            db.execSQL(IMAGES_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // when database version is updated upgrade tables
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);

            //recreate the tables
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        //will return false when no database has been created
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        theSitesDB = dbHelper.getWritableDatabase();

        return (theSitesDB == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    public boolean checkEmpty(SQLiteDatabase db, String table){
        return DatabaseUtils.queryNumEntries(db, table) == 0;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        String insert = "";

        if (contentValues.get("type") == "Site") {

            insert = "insert into " + DATABASE_TABLE1 + " (" + _siteNAME + ", " + _siteTITLE + ") values('"
                    + contentValues.get("param1") + "', '" + contentValues.get("title") + "');";
            Log.d("Insert Statement", insert);
        }
        else if(contentValues.get("type") == "Image") {
            Log.d("Type of Insert", "Image");
            String selectQ = "Select " + _siteID + " From " + DATABASE_TABLE1 + " Where " + _siteTITLE + " = '" + contentValues.get("title") + "';";
            Cursor cursor = theSitesDB.rawQuery(selectQ,null);
            int siteNum = 0;
            if(cursor.moveToFirst()){
                do{
                    siteNum = cursor.getInt(0);
                }while (cursor.moveToNext());
            }

            insert = "insert into " + DATABASE_TABLE2 + " (" + _siteID + ", " + _photoNAME + ") values("
                    + siteNum + ", '" + contentValues.get("param2") + "');";
        }

        if(theSitesDB == null){

        }
        theSitesDB.execSQL(insert);
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String str, String[] string) {
        return 0;
    }
}
