package com.example.sqldemo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static final String INVENTORY_ITEM_TABLE = "INVENTORY_ITEM_TABLE";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_PRICE = "ITEM_PRICE";
    public static final String COLUMN_ITEM_QUANTITY = "ITEM_QUANTITY";
    public static final String COLUMN_ITEM_ID = "ITEM_ID";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "inventory_item.db", null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    //this is called when database is accessed, code for making a database should be here
    @Override
    public void onCreate(SQLiteDatabase db) {

    String createInventoryTable= "CREATE TABLE " + INVENTORY_ITEM_TABLE + "(" + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_ITEM_PRICE + " INT," +COLUMN_ITEM_QUANTITY + " INT)";
    db.execSQL(createInventoryTable);
    }


    //this is called when a new version is made to prevent users app from crashing
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_ITEM_TABLE);
    }

    //Inventory item table operations

    public boolean insertData(String name,String price,String quantity){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_ITEM_NAME,name);
        cv.put(COLUMN_ITEM_PRICE,price);
        cv.put(COLUMN_ITEM_QUANTITY,quantity);


        long insert=  db.insert(INVENTORY_ITEM_TABLE,null,cv);
        db.close();

        if (insert != -1) return true;
        else return false;
    }

public int deleteOne(String id){

    SQLiteDatabase db=this.getWritableDatabase();
    return   db.delete(INVENTORY_ITEM_TABLE,COLUMN_ITEM_ID+"=?",new String[]{id});


}



   public ArrayList<InventoryItem> getAll(){
       SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<InventoryItem> returnList=new ArrayList<>();
        String queryString="SELECT * FROM "+INVENTORY_ITEM_TABLE;



        Cursor cursor = db.rawQuery(queryString,null);

        while (cursor.moveToNext()){

                int id=cursor.getInt(0);
                String name= cursor.getString(1);
                String price= cursor.getString(2);
                String quantity= cursor.getString(3);


                InventoryItem item=new InventoryItem(id,name,price,quantity);
                returnList.add(item);

        }

        cursor.close();
        db.close();
        return returnList;

    }

    public boolean updateData(String id,String name,String price,String quantity){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_ITEM_NAME,name);
        cv.put(COLUMN_ITEM_PRICE,price);
        cv.put(COLUMN_ITEM_QUANTITY,quantity);



        db.update(INVENTORY_ITEM_TABLE,cv,COLUMN_ITEM_ID+"=?",new String[]{id});
        return true;
    }














}
