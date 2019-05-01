package iomis.com.a42virtual.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TotalClothesData {

    //ArrayLists for Total Clothes Table
    public ArrayList<String> Names = new ArrayList<>();
    public ArrayList<Integer> TemparatureMin = new ArrayList<>();
    public ArrayList<Integer> TemparatureMax = new ArrayList<>();
    public ArrayList<String> Weather = new ArrayList<>();
    public ArrayList<String> Type = new ArrayList<>();


    public ArrayList<String> getNames() {
        return Names;
    }

    public void setNames(ArrayList<String> names) {
        Names = names;
    }

    public ArrayList<Integer> getTemparatureMin() {
        return TemparatureMin;
    }

    public void setTemparatureMin(ArrayList<Integer> temparatureMin) {
        TemparatureMin = temparatureMin;
    }

    public ArrayList<Integer> getTemparatureMax() {
        return TemparatureMax;
    }

    public void setTemparatureMax(ArrayList<Integer> temparatureMax) {
        TemparatureMax = temparatureMax;
    }

    public ArrayList<String> getWeather() {
        return Weather;
    }

    public void setWeather(ArrayList<String> weather) {
        Weather = weather;
    }

    public ArrayList<String> getType() {
        return Type;
    }

    public void setType(ArrayList<String> type) {
        Type = type;
    }

    //Function to initialize clothes data ( need to first call)
    public void InitializeClothesData(){
        //Куртка
        Names.add("jacket");
        TemparatureMin.add(0);
        TemparatureMax.add(15);
        Weather.add("Fair/cloudy/rain");
        Type.add("outerwear");


        //Джинсова куртка
        Names.add("jeans jacket");
        TemparatureMin.add(7);
        TemparatureMax.add(15);
        Weather.add("Fair/cloudy");
        Type.add("outerwear");

        //Зимова Куртка
        Names.add("Winter jacket");
        TemparatureMin.add(-20);
        TemparatureMax.add(0);
        Weather.add("Fair/cloudy/snow/light snow/mainly cloudy");
        Type.add("outerwear");

        //Джинси
        Names.add("Jeans");
        TemparatureMin.add(-5);
        TemparatureMax.add(18);
        Weather.add("Fair/cloudy/rain/partly cloudy");
        Type.add("undercoat");

        //Футболка
        Names.add("T-shirt");
        TemparatureMin.add(15);
        TemparatureMax.add(30);
        Weather.add("Fair/partly cloudy");
        Type.add("outerwear");

        //Сорочка
        Names.add("shirt");
        TemparatureMin.add(10);
        TemparatureMax.add(20);
        Weather.add("Fair/partly cloudy/rain");
        Type.add("outerwear");

        //Шорти
        Names.add("Trunks");
        TemparatureMin.add(20);
        TemparatureMax.add(30);
        Weather.add("Fair/partly cloudy/rain/mainly cloudy");
        Type.add("undercoat");

        //Світер ( світшот)
        Names.add("sweatshirt");
        TemparatureMin.add(5);
        TemparatureMax.add(15);
        Weather.add("Fair/partly");
        Type.add("outerwear");


        //Брюки
        Names.add("sweatshirt");
        TemparatureMin.add(-5);
        TemparatureMax.add(18);
        Weather.add("Fair/cloudy/rain/partly cloudy");
        Type.add("undercoat");

        //Кроси
        Names.add("runing shoes");
        TemparatureMin.add(5);
        TemparatureMax.add(24);
        Weather.add("Fair/cloudy/rain/partly cloudy/mainly cloudy");
        Type.add("shoes");

        //Кеди
        Names.add("sneakers");
        TemparatureMin.add(5);
        TemparatureMax.add(24);
        Weather.add("Fair/cloudy/rain/partly cloudy/mainly cloudy");
        Type.add("shoes");

        //туфлі
        Names.add("heels");
        TemparatureMin.add(5);
        TemparatureMax.add(15);
        Weather.add("Fair/cloudy/partly cloudy");
        Type.add("shoes");

        //зимнє взуття
        Names.add("winter shoes");
        TemparatureMin.add(-20);
        TemparatureMax.add(0);
        Weather.add("snow/cloudy/partly cloudy/rain/light snow/mainly cloudy");
        Type.add("shoes");





    }


    //Add all initialized data to Total Clothes Table
    public void addTotalClothesToDB(DBHelper dbHelper, SQLiteDatabase db){


        for(int i=0;i<Names.size();i++) {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_NAME,Names.get(i));
            cv.put(DBHelper.COLUMN_TEMPERATURE_MIN,TemparatureMin.get(i));
            cv.put(DBHelper.COLUMN_TEMPERATURE_MAX,TemparatureMax.get(i));
            cv.put(DBHelper.COLUMN_WEATHER,Weather.get(i));
            cv.put(DBHelper.COLUMN_TYPE,Type.get(i));
            db.insert(DBHelper.TABLE_NAME_TC, null, cv);
        }

    }
}
