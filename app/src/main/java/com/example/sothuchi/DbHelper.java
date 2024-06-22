package com.example.sothuchi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context ) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_DANHMUC);
        db.execSQL(Constants.CREATE_TABLE_THUCHI);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertDanhMuc(String tendm, int loai){

        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();

        // id will save automatically as we write query
        contentValues.put(Constants.tendm,tendm);
        contentValues.put(Constants.loai,loai);

        //insert data in row, It will return id of record
        long id = db.insert(Constants.TABLE_DANHMUC,null,contentValues);

        // close db
        db.close();

        //return id
        return id;

    }
    public long insertThuChi(float sotien, int loai, String ngaytc, String ghichu, int madm){

        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();

        // id will save automatically as we write query
        contentValues.put(Constants.sotien,sotien);
        contentValues.put(Constants.loai,loai);
        contentValues.put(Constants.ngaytc,ngaytc);
        contentValues.put(Constants.ghichu,ghichu);
        contentValues.put(Constants.madm,madm);

        //insert data in row, It will return id of record
        long id = db.insert(Constants.TABLE_THUCHI,null,contentValues);

        // close db
        db.close();

        //return id
        return id;

    }
}
