package iomis.com.a42virtual.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DatabaseFinder {





    public static Cursor dbFindClothesByName(SQLiteDatabase db, String clothes_name, Cursor cursor) {

        String selectQueryUser = "SELECT  * FROM " + DBHelper.TABLE_NAME_TC + " WHERE "
                + DBHelper.COLUMN_NAME + " =\'" + clothes_name + "\'";
        cursor = db.rawQuery(selectQueryUser, null);

        return cursor;
    }


    public static ArrayList<String> getClothesFromDB(SQLiteDatabase db,DBHelper dbHelper) {

        ArrayList<String> arrayList=new ArrayList<>();

     db = dbHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " +DBHelper.TABLE_NAME_TC, null );
        res.moveToFirst();

        if (res != null)
        {
            while(res.isAfterLast() == false){
                arrayList.add(res.getString(res.getColumnIndex(DBHelper.COLUMN_NAME)));


                res.moveToNext();
            }}
        return arrayList;

    }



    //****************Function can be deleted************************
    //Developer function to check data in Total CLothes Table
    public static String tableToString(SQLiteDatabase db, String tableName) {
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        tableString += cursorToString(allRows);
        System.out.println(tableString);
        return tableString;
    }

    public static String cursorToString(Cursor cursor) {
        String cursorString = "";
        if (cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String name : columnNames)
                cursorString += String.format("%s ][ ", name);
            cursorString += "\n";
            do {
                for (String name : columnNames) {
                    cursorString += String.format("%s ][ ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }

}

