package com.example.scherr3143.contentprovider_downloadservice;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by scherr3143 on 5/4/2017.
 */
public class urlContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.scherr3143.contentprovider_downloadservice.urlContentProvider";
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
    static final int DATABASE_VERSION = 3;

    static final String SITES_TABLE_CREATE = "Create table " + DATABASE_TABLE1 + "(" + _siteID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _siteNAME + " TEXT " + _siteTITLE + " TEXT)";

    static final String IMAGES_TABLE_CREATE = "Create table " + DATABASE_TABLE2 + "(" + _photoID + " INTEGER PRIMARY KEY, "
            + _siteID + " INTEGER, " + _photoNAME + " TEXT, FOREIGN KEY (" + _siteID + ") REFERENCES " + DATABASE_TABLE1 + "(" + _siteID + "))";


    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //create the tables whn the database is first created
            db.execSQL(SITES_TABLE_CREATE);
            db.execSQL(IMAGES_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // when database version is updated upgrade tables
            db.execSQL("DROP TABLE IF EXISTS " + SITES_TABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE_CREATE);

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
        return theSitesDB != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        return null;
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
            insert = "insert into " + DATABASE_TABLE1 + " (" + _siteNAME + ", " + _siteTITLE + ") values("
                    + contentValues.get("param1") + ", " + contentValues.get("title") + ");";
        }
        else if(contentValues.get("type") == "Image") {

            String selectQ = "Select " + _siteID + " From " + DATABASE_TABLE1 + " Where " + _siteTITLE + " = " + contentValues.get("title") + ";";
            Cursor cursor = theSitesDB.rawQuery(selectQ,null);
            int siteNum = 0;
            if(cursor.moveToFirst()){
                do{
                    siteNum = cursor.getInt(0);
                }while (cursor.moveToNext());
            }

            insert = "insert into " + DATABASE_TABLE2 + " (" + _siteID + ", " + _photoNAME + ") values("
                    + siteNum + ", " + contentValues.get("param2") + ");";
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
