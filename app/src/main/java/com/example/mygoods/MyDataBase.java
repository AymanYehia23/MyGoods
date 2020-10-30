package com.example.mygoods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyDataBase extends SQLiteOpenHelper {

    public final static String DB_NAME = "shoes_DB";
    public final static int DB_VERSION = 1;
    public final static String SHOES_TB_NAME = "shoes";
    public final static String ORDERS_TB_NAME = "orders";
    public final static String SHOES_CLN_ID = "id";
    public final static String SHOES_CLN_MODEL = "model";
    public final static String SHOES_CLN_SIZE = "size";
    public final static String SHOES_CLN_COLOR = "color";
    public final static String SHOES_CLN_QUANTITY = "quantity";
    public final static String SHOES_CLN_PRICE = "price";
    public final static String SHOES_CLN_DATE = "date";
    public final static String SHOES_CLN_TIME = "time";


    public MyDataBase(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+SHOES_TB_NAME+"("+SHOES_CLN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
        + SHOES_CLN_MODEL+ " TEXT, "+SHOES_CLN_SIZE+" INTEGER, "+SHOES_CLN_COLOR+" TEXT, "+SHOES_CLN_QUANTITY+ " INTEGER, "
        +SHOES_CLN_PRICE+" REAL)");

        sqLiteDatabase.execSQL("CREATE TABLE "+ORDERS_TB_NAME+"("+SHOES_CLN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SHOES_CLN_MODEL+ " TEXT, "+SHOES_CLN_SIZE+" INTEGER, "+SHOES_CLN_COLOR+" TEXT, "+SHOES_CLN_QUANTITY+ " INTEGER, "
                +SHOES_CLN_PRICE+" REAL, "+SHOES_CLN_DATE+" TEXT, "+SHOES_CLN_TIME+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SHOES_TB_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ORDERS_TB_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertShoes(Shoes shoes){
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Shoes>shoesArrayList = getAllShoes();
        if(shoesArrayList.size()!=0)
        {
            for(int i=0; i<shoesArrayList.size();i++) {
                if (shoes.getModel().equals(shoesArrayList.get(i).getModel())
                        && shoes.getSize() == shoesArrayList.get(i).getSize()
                        && shoes.getColor().equals(shoesArrayList.get(i).getColor())
                        && shoes.getPrice() == shoesArrayList.get(i).getPrice()) {
                    int q = shoesArrayList.get(i).getQuantity();
                    q += shoes.getQuantity();
                    boolean b = updateShoes(shoesArrayList.get(i), q);
                    return b;
                }
                else
                {
                    ContentValues values = new ContentValues();
                    values.put(SHOES_CLN_MODEL,shoes.getModel());
                    values.put(SHOES_CLN_SIZE,shoes.getSize());
                    values.put(SHOES_CLN_COLOR,shoes.getColor());
                    values.put(SHOES_CLN_QUANTITY,shoes.getQuantity());
                    values.put(SHOES_CLN_PRICE,shoes.getPrice());
                    long result = db.insert(SHOES_TB_NAME,null,values);
                    return result != -1;
                }
            }
        }
            else
            {
                ContentValues values = new ContentValues();
                values.put(SHOES_CLN_MODEL,shoes.getModel());
                values.put(SHOES_CLN_SIZE,shoes.getSize());
                values.put(SHOES_CLN_COLOR,shoes.getColor());
                values.put(SHOES_CLN_QUANTITY,shoes.getQuantity());
                values.put(SHOES_CLN_PRICE,shoes.getPrice());
                long result = db.insert(SHOES_TB_NAME,null,values);
                return result != -1;
            }

        return false;
    }

    public boolean insertOrder(Shoes shoes)
    {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOES_CLN_MODEL,shoes.getModel());
        values.put(SHOES_CLN_SIZE,shoes.getSize());
        values.put(SHOES_CLN_COLOR,shoes.getColor());
        values.put(SHOES_CLN_QUANTITY,shoes.getQuantity());
        values.put(SHOES_CLN_PRICE,shoes.getPrice());
        values.put(SHOES_CLN_DATE,currentDate);
        values.put(SHOES_CLN_TIME,currentTime);
        long result = db.insert(ORDERS_TB_NAME,null,values);

        return result != -1;
    }

    public boolean updateShoes(Shoes shoes,int q){
        SQLiteDatabase db = getWritableDatabase();
        shoes.setQuantity(q);
        ContentValues values = new ContentValues();
        values.put(SHOES_CLN_MODEL,shoes.getModel());
        values.put(SHOES_CLN_SIZE,shoes.getSize());
        values.put(SHOES_CLN_COLOR,shoes.getColor());
        values.put(SHOES_CLN_QUANTITY,shoes.getQuantity());
        values.put(SHOES_CLN_PRICE,shoes.getPrice());

        String args[] = {shoes.getId()+""};
        long result = db.update(SHOES_TB_NAME,values,"id=?",args);


        return result > 0;
    }

    public boolean deleteShoes(Shoes shoes){
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {shoes.getId()+""};
        int result = db.delete(SHOES_TB_NAME,"id=?",args);
        return result > 0;
    }

    public boolean deleteOrderedShoes(Shoes shoes){
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {shoes.getId()+""};
        int result = db.delete(ORDERS_TB_NAME,"id=?",args);
        return result > 0;
    }

    public boolean deleteAllOrders(){
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(ORDERS_TB_NAME,null,null);
        return result > 0;
    }

    public boolean deleteAllShoes(){
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(SHOES_TB_NAME,null,null);
        return result > 0;
    }

    public ArrayList <Shoes> getAllShoes(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList <Shoes> shoes_list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM "+SHOES_TB_NAME,null);

        if(c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(SHOES_CLN_ID));
                String model = c.getString(c.getColumnIndex(SHOES_CLN_MODEL));
                int size = c.getInt(c.getColumnIndex(SHOES_CLN_SIZE));
                String color = c.getString(c.getColumnIndex(SHOES_CLN_COLOR));
                int quantity = c.getInt(c.getColumnIndex(SHOES_CLN_QUANTITY));
                double price = c.getDouble(c.getColumnIndex(SHOES_CLN_PRICE));

                Shoes shoes = new Shoes(id,model,size,color,quantity,price);
                shoes_list.add(shoes);

            } while (c.moveToNext());
            c.close();
        }
        return shoes_list;
    }

    public ArrayList <Shoes> getAllOrders(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList <Shoes> shoes_list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM "+ORDERS_TB_NAME,null);

        if(c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(SHOES_CLN_ID));
                String model = c.getString(c.getColumnIndex(SHOES_CLN_MODEL));
                int size = c.getInt(c.getColumnIndex(SHOES_CLN_SIZE));
                String color = c.getString(c.getColumnIndex(SHOES_CLN_COLOR));
                int quantity = c.getInt(c.getColumnIndex(SHOES_CLN_QUANTITY));
                double price = c.getDouble(c.getColumnIndex(SHOES_CLN_PRICE));
                String date = c.getString(c.getColumnIndex(SHOES_CLN_DATE));
                String time = c.getString(c.getColumnIndex(SHOES_CLN_TIME));

                Shoes shoes = new Shoes(id,model,size,color,quantity,price,date,time);
                shoes_list.add(shoes);

            } while (c.moveToNext());
            c.close();
        }
        return shoes_list;
    }

}
