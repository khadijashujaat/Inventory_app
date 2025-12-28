package com.example.sqldemo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper2 extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper2(@Nullable Context context) {
        super(context, "users.db", null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(email TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE if  exists Users");
    }

    public Boolean insertData(String email,String password){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("email",email);
        cv.put("password",password);

        long result = db.insert("Users", null, cv);

        if (result==-1){
            return false;
        }else
            return true;

    }

    public Boolean checkEmail(String email){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Users WHERE email=?",new String[]{email});

        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Users WHERE email=? and password=?" ,new String[] {email,password});

        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
}
