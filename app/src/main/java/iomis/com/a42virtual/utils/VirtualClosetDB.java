package iomis.com.a42virtual.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import iomis.com.a42virtual.Clothes;

import java.util.ArrayList;
import java.util.List;

public class VirtualClosetDB {

    public static void insert(SQLiteDatabase db, DBHelper dbHelper, String title, String name, int color, int tempMin, int tempMax, String weather, String Type, Boolean sync) {

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_TITLE, title);
        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_COLOR, color);
        cv.put(DBHelper.COLUMN_TEMPERATURE_MIN, tempMin);
        cv.put(DBHelper.COLUMN_TEMPERATURE_MAX, tempMax);
        cv.put(DBHelper.COLUMN_WEATHER, weather);
        cv.put(DBHelper.COLUMN_TYPE, Type);
        cv.put(DBHelper.COLUMN_PATH, "/here/is/path");
        cv.put(DBHelper.COLUMN_SYNCED, sync);
        db.insert(DBHelper.TABLE_NAME_CIC, null, cv);

    }


    public static void update(SQLiteDatabase db, DBHelper dbHelper, int KEY_ID, String title, String name, int color, int tempMin, int tempMax, String weather, String Type, String path) {

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_TITLE, title);
        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_COLOR, color);
        cv.put(DBHelper.COLUMN_TEMPERATURE_MIN, tempMin);
        cv.put(DBHelper.COLUMN_TEMPERATURE_MAX, tempMax);
        cv.put(DBHelper.COLUMN_WEATHER, weather);
        cv.put(DBHelper.COLUMN_TYPE, Type);
        cv.put(DBHelper.COLUMN_PATH, path);
        cv.put(DBHelper.COLUMN_SYNCED, 1);
        db.update(DBHelper.TABLE_NAME_CIC, cv, DBHelper.KEY_ID + "=" + KEY_ID, null);

    }


    public static void delete(SQLiteDatabase db, DBHelper dbHelper, int KEY_ID, String title) {

        db.delete(DBHelper.TABLE_NAME_CIC, DBHelper.KEY_ID + "=" + KEY_ID + " and " + DBHelper.COLUMN_TITLE + "=" + title, null);


    }



    //Get List of Object of Clothes in out table Clothes in Closet
    public static List getObjectsFromCIC(SQLiteDatabase db) {
        List<Clothes> dataList = new ArrayList<Clothes>();
        String title, name, weather, Type, path;
        int color, tempMin, tempMax, keyid;
        boolean sync;

        Cursor c = db.rawQuery("select * from " + DBHelper.TABLE_NAME_CIC, null);

        if (c.moveToFirst()) {
            do {
                keyid = c.getInt(c.getColumnIndex(DBHelper.KEY_ID));
                title = c.getString(c.getColumnIndex(DBHelper.COLUMN_TITLE));
                name = c.getString(c.getColumnIndex(DBHelper.COLUMN_NAME));
                color = c.getInt(c.getColumnIndex(DBHelper.COLUMN_COLOR));
                tempMin = c.getInt(c.getColumnIndex(DBHelper.COLUMN_TEMPERATURE_MIN));
                tempMax = c.getInt(c.getColumnIndex(DBHelper.COLUMN_TEMPERATURE_MAX));
                weather = c.getString(c.getColumnIndex(DBHelper.COLUMN_WEATHER));
                Type = c.getString(c.getColumnIndex(DBHelper.COLUMN_TYPE));
                path = c.getString(c.getColumnIndex(DBHelper.COLUMN_PATH));

                dataList.add(new Clothes(title, name, color, tempMin, tempMax, weather, Type));


            } while (c.moveToNext());


        }

        return dataList;
    }
}
