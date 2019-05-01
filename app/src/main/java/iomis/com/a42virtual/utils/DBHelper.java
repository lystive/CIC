package iomis.com.a42virtual.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "Virtual_Closet_DB";
    //TC - Total Clothes
    public static final String TABLE_NAME_TC = "Total_Clothes";
    //CIC - Clothes in Closet
    public static final String TABLE_NAME_CIC = "Clothes_in_Closet";
    public static final String KEY_ID = "clothes_id";
    //Title - the name of the garment
    public static final String COLUMN_TITLE = "title";
    //name - total name of clothes
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_TEMPERATURE_MIN = "temperature_min";
    public static final String COLUMN_TEMPERATURE_MAX = "temperature_max";
    //Type - outerwear, undercoat
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_WEATHER = "weather";

    //column to check is row synced with FIREBASE

    public static final String COLUMN_SYNCED = "sync";



    public  DBHelper (Context context){
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table Total CLothes
        db.execSQL("create table "+TABLE_NAME_TC+ "("
                + KEY_ID+" integer primary key autoincrement,"
                + COLUMN_NAME+" text,"
                + COLUMN_TEMPERATURE_MIN+ " INTEGER, "+ COLUMN_TEMPERATURE_MAX+ " INTEGER, "+ COLUMN_WEATHER + " text, " + COLUMN_TYPE + " text" + ");");


        //Create table Virtual closet
        db.execSQL("create table "+TABLE_NAME_CIC+ "("
                + KEY_ID+" integer primary key autoincrement,"
                + COLUMN_TITLE+" text," + COLUMN_NAME+" text," + COLUMN_COLOR+" integer,"
                + COLUMN_TEMPERATURE_MIN+ " INTEGER, "+ COLUMN_TEMPERATURE_MAX+ " INTEGER, "+ COLUMN_WEATHER + " text, "+ COLUMN_TYPE + " text, " + COLUMN_PATH + " text, "+ COLUMN_SYNCED + " BOOL" + ");");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
;
    }
}
