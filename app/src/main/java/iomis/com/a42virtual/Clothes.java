package iomis.com.a42virtual;

public class Clothes {

    public String title,name,weather,Type;
    int color, tempMin, tempMax;
    boolean sync;
    public Clothes(String title, String name,int color,int tempMin, int tempMax, String weather,String Type){
        this.title = title;
        this.name = name;
        this.weather = weather;
        this.Type = Type;
        this.color = color;
        this.tempMin = tempMin;
        this.color = color;
        this.sync= true;

    }
}
