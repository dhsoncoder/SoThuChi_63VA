package com.example.sothuchi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "QuanLySoThuChi.db";
    private static final int DATABASE_VERSION = 2; // Incremented version number

    // Table and column names
    public static final String TABLE_DANHMUC = "danhmuc";
    public static final String COLUMN_MA_DANHMUC = "ma_danhmuc";
    public static final String COLUMN_TEN_DANHMUC = "ten_danhmuc";
    public static final String COLUMN_LOAI = "loai";
    public static final String COLUMN_BIEUTUONG = "bieutuong"; // New column for icon
    public static final String COLUMN_MAUSAC = "mausac"; // New column for icon color

    public static final String TABLE_THUCHI = "thuchi";
    public static final String COLUMN_MA_THUCHI = "ma_thuchi";
    public static final String COLUMN_MA_DANHMUC_THUCHI = "ma_danhmuc";
    public static final String COLUMN_SO_TIEN = "so_tien";
    public static final String COLUMN_NGAY_THUCHI = "ngay_thuchi";
    public static final String COLUMN_GHICHU = "ghichu";

    // SQL create table statements
    private static final String CREATE_TABLE_DANHMUC =
            "CREATE TABLE " + TABLE_DANHMUC + " (" +
                    COLUMN_MA_DANHMUC + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEN_DANHMUC + " TEXT, " +
                    COLUMN_LOAI + " INTEGER, " +
                    COLUMN_BIEUTUONG + " TEXT, " +
                    COLUMN_MAUSAC + " TEXT)";

    private static final String CREATE_TABLE_THUCHI =
            "CREATE TABLE " + TABLE_THUCHI + " (" +
                    COLUMN_MA_THUCHI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MA_DANHMUC_THUCHI + " INTEGER, " +
                    COLUMN_SO_TIEN + " REAL, " +
                    COLUMN_LOAI + " INTEGER, " +
                    COLUMN_NGAY_THUCHI + " TEXT, " +
                    COLUMN_GHICHU + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_MA_DANHMUC_THUCHI + ") REFERENCES " + TABLE_DANHMUC + "(" + COLUMN_MA_DANHMUC + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DANHMUC);
        db.execSQL(CREATE_TABLE_THUCHI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_DANHMUC + " ADD COLUMN " + COLUMN_BIEUTUONG + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_DANHMUC + " ADD COLUMN " + COLUMN_MAUSAC + " TEXT");
        }
    }

    // Insert a record into danhmuc
    public long insertDanhmuc(String tenDanhmuc, int loai, String bieutuong, String mausac) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_DANHMUC, tenDanhmuc);
        values.put(COLUMN_LOAI, loai);
        values.put(COLUMN_BIEUTUONG, bieutuong);
        values.put(COLUMN_MAUSAC, mausac);
        return db.insert(TABLE_DANHMUC, null, values);
    }

    // Insert a record into thuchi
    public long insertThuchi(int maDanhmuc, double soTien, int loai, String ngayThuchi, String ghichu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_DANHMUC_THUCHI, maDanhmuc);
        values.put(COLUMN_SO_TIEN, soTien);
        values.put(COLUMN_LOAI, loai);
        values.put(COLUMN_NGAY_THUCHI, ngayThuchi);
        values.put(COLUMN_GHICHU, ghichu);
        return db.insert(TABLE_THUCHI, null, values);
    }

    // Get all records from danhmuc
    public Cursor getAllDanhmuc() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DANHMUC, null);
    }

    // Get all records from thuchi
    public Cursor getAllThuchi() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_THUCHI, null);
    }

    // Update a record in danhmuc
    public int updateDanhmuc(int maDanhmuc, String tenDanhmuc, int loai, String bieutuong, String mausac) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_DANHMUC, tenDanhmuc);
        values.put(COLUMN_LOAI, loai);
        values.put(COLUMN_BIEUTUONG, bieutuong);
        values.put(COLUMN_MAUSAC, mausac);
        return db.update(TABLE_DANHMUC, values, COLUMN_MA_DANHMUC + " = ?", new String[]{String.valueOf(maDanhmuc)});
    }

    // Update a record in thuchi
    public int updateThuchi(int maThuchi, int maDanhmuc, double soTien, int loai, String ngayThuchi, String ghichu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_DANHMUC_THUCHI, maDanhmuc);
        values.put(COLUMN_SO_TIEN, soTien);
        values.put(COLUMN_LOAI, loai);
        values.put(COLUMN_NGAY_THUCHI, ngayThuchi);
        values.put(COLUMN_GHICHU, ghichu);
        return db.update(TABLE_THUCHI, values, COLUMN_MA_THUCHI + " = ?", new String[]{String.valueOf(maThuchi)});
    }

    // Delete a record from danhmuc
    public void deleteDanhmuc(int maDanhmuc) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DANHMUC, COLUMN_MA_DANHMUC + " = ?", new String[]{String.valueOf(maDanhmuc)});
    }

    // Delete a record from thuchi
    public void deleteThuchi(int maThuchi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THUCHI, COLUMN_MA_THUCHI + " = ?", new String[]{String.valueOf(maThuchi)});
    }
}
