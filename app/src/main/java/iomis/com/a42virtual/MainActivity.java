package iomis.com.a42virtual;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;
import com.skydoves.colorpickerpreference.ColorPickerView;
import iomis.com.a42virtual.utils.DBHelper;
import iomis.com.a42virtual.utils.DatabaseFinder;
import iomis.com.a42virtual.utils.TotalClothesData;
import iomis.com.a42virtual.utils.VirtualClosetDB;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private final static int INTERVAL = 1000 * 60*6;//sync every 6 minutes
    SQLiteDatabase db;
    DBHelper dbHelper;
    String choose_clothes,title;
    int color,temperaputemin=0,temperaturemax=0 ;
    Spinner spinner;
    TotalClothesData TCD;
    Cursor u,c;
    ArrayList <String> clothes_name;
    String weather,type,name;

    EditText clothes_title,clothes_color;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Check if Database is already exist , dont add new initialized data
        if(!doesDatabaseExist(this,DBHelper.DATABASE_NAME)) {
            TCD = new TotalClothesData();
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
            TCD.InitializeClothesData();
            TCD.addTotalClothesToDB(dbHelper, db);
        }


        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();


  DatabaseFinder.tableToString(db,DBHelper.TABLE_NAME_TC);

   //get all data from column name of clothes
        clothes_name = new ArrayList<String>();
        clothes_name= DatabaseFinder.getClothesFromDB(db,dbHelper);

        spinner = (Spinner) findViewById(R.id.ClothesName);
        clothes_title  = (EditText) findViewById(R.id.edit_text_title);
        addButton = (Button) findViewById(R.id.add_button);
        clothes_color= (EditText)findViewById(R.id.edit_text_color) ;

        ColorPickerView colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);

        //our spinner ( list of type to pick)
        InitializeSpinner();

        //Check internet connection every 6 minutes and sync data with firebase
       addButton.setOnClickListener(this);
       clothes_color.setOnClickListener(this);

        final Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable()){
                    syncWithFirebase();
                }
                handler.postDelayed(this, INTERVAL);

            }
        }, INTERVAL);


    }


    @Override
    protected void onDestroy(){
        db.close();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        title = clothes_title.getText().toString().trim();

        switch (v.getId()) {

            case R.id.add_button:
                    insertIntoCloset(false);
                 break;



            case R.id.edit_text_color:
                InitializeColorPicker();
                clothes_color.setText(Integer.toString(color));
                break;

        }
    }


    public void InitializeColorPicker(){

        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("ColorPicker Dialog");
        builder.setPreferenceName("MyColorPickerDialog");

        builder.setPositiveButton(getString(R.string.confirm), new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {

                color = colorEnvelope.getColor();
                clothes_color.setBackgroundColor(color);
                clothes_color.setText(Integer.toString(color));

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }



public void InitializeSpinner(){
    // Create adapter ArrayAdapter
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clothes_name);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Use adapter
    spinner.setAdapter(adapter);

    OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            // Get choise
            choose_clothes = (String)parent.getItemAtPosition(position);
            c = DatabaseFinder.dbFindClothesByName(db,choose_clothes,c);
            if(c.moveToFirst()) {
                temperaputemin = c.getInt(2);
                temperaturemax = c.getInt(3);
                weather = c.getString(4);
                type = c.getString(5);
                c.close();

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    spinner.setOnItemSelectedListener(itemSelectedListener);
}



    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public static boolean isNotEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }


    public void showToast(String text) {
        //создаём и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //Check for avaliable network

    public  boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }


    //Insert into
  public void insertIntoCloset(boolean sync){
      if(isNotEmpty(clothes_title) && isNotEmpty(clothes_color)){
          VirtualClosetDB.insert(db, dbHelper, title, choose_clothes, color, temperaputemin, temperaturemax, weather, type,sync);
          showToast("Added successfully");

          //All rows from table Closet
          String newString;
          newString = DatabaseFinder.tableToString(db,DBHelper.TABLE_NAME_CIC);
          System.out.println(newString);
      }
      else{
          showToast("Error! One or more field is empty!");
      }

  }



  public void syncWithFirebase(){
      List<Clothes> dataList = new ArrayList<Clothes>();
     String title,name,weather,Type,path;
      int color, tempMin, tempMax,keyid;
      boolean sync;

      Cursor c = db.rawQuery("select * from "+DBHelper.TABLE_NAME_CIC,null);

      if (c.moveToFirst()) {
          do {
              keyid = c.getInt(c.getColumnIndex(DBHelper.KEY_ID));
              title = c.getString(c.getColumnIndex(DBHelper.COLUMN_TITLE));
              name = c.getString(c.getColumnIndex(DBHelper.COLUMN_NAME));
                     color =  c.getInt(c.getColumnIndex(DBHelper.COLUMN_COLOR));
                    tempMin=   c.getInt(c.getColumnIndex(DBHelper.COLUMN_TEMPERATURE_MIN));
                    tempMax=  c.getInt(c.getColumnIndex(DBHelper.COLUMN_TEMPERATURE_MAX));
                     weather =  c.getString(c.getColumnIndex(DBHelper.COLUMN_WEATHER));
                     Type =  c.getString(c.getColumnIndex(DBHelper.COLUMN_TYPE));
              path =  c.getString(c.getColumnIndex(DBHelper.COLUMN_PATH));

              //if row isnt synced ( sync=0)
              if((c.getInt(c.getColumnIndex(DBHelper.COLUMN_SYNCED)))==0){
              dataList.add(new Clothes(title,name,color,tempMin,tempMax,weather,Type));
              //make already synced ( replace field sync from 0 to 1)
              VirtualClosetDB.update(db,dbHelper,keyid,title,name,color,tempMin,tempMax,weather,Type,path);
              }
          } while (c.moveToNext());
      }

      if(dataList.size() > 0 ){
          FirebaseApp.initializeApp(this);
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(DBHelper.TABLE_NAME_CIC);
          for(Clothes d : dataList){
              ref.push().setValue(d);
          }
      }
  }





}
