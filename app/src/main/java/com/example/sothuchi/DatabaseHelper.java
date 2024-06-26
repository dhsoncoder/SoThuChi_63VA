package com.example.sothuchi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "QuanLySoThuChi.db";
    private static final int DATABASE_VERSION = 2; // Incremented version number

    // Table and column names
    public static final String TABLE_DANHMUC = "danhmuc";
    public static final String COLUMN_MA_DANHMUC = "ma_danhmuc";
    public static final String COLUMN_TEN_DANHMUC = "ten_danhmuc";
    public static final String COLUMN_LOAI = "loai";
    public static final String COLUMN_BIEUTUONG = "bieutuong"; // Changed to int for drawable resource ID
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
                    COLUMN_BIEUTUONG + " INTEGER, " + // Changed type to INTEGER
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
            db.execSQL("ALTER TABLE " + TABLE_DANHMUC + " ADD COLUMN " + COLUMN_BIEUTUONG + " INTEGER"); // Changed type to INTEGER
            db.execSQL("ALTER TABLE " + TABLE_DANHMUC + " ADD COLUMN " + COLUMN_MAUSAC + " TEXT");
        }
    }

    // Insert a record into danhmuc
    public long insertDanhmuc(String tenDanhmuc, int loai, int bieutuong, String mausac) { // Changed bieutuong to int
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
    public int updateDanhmuc(int maDanhmuc, String tenDanhmuc, int loai, int bieutuong, String mausac) { // Changed bieutuong to int
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

    // Get the name of a specific danhmuc
    public String getDanhmucName(int maDanhmuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tenDanhmuc = "";

        Cursor cursor = db.query(TABLE_DANHMUC,
                new String[]{COLUMN_TEN_DANHMUC},
                COLUMN_MA_DANHMUC + " = ?",
                new String[]{String.valueOf(maDanhmuc)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            tenDanhmuc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_DANHMUC));
            cursor.close();
        }

        return tenDanhmuc;
    }

    // Get the color of a specific danhmuc
    public String getDanhmucColor(int maDanhmuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String mauSac = "";

        Cursor cursor = db.query(TABLE_DANHMUC,
                new String[]{COLUMN_MAUSAC},
                COLUMN_MA_DANHMUC + " = ?",
                new String[]{String.valueOf(maDanhmuc)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            mauSac = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAUSAC));
            cursor.close();
        }

        return mauSac;
    }

    // Get total amount from thuchi
    public double getTotalAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalAmount = 0.0;

        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SO_TIEN + ") FROM " + TABLE_THUCHI, null);
        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0);
        }
        cursor.close();
        return totalAmount;
    }

    // Get count of occurrences for a specific maDanhmuc
    public int getCountByDanhmuc(int maDanhmuc, String tenDanhmuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_DANHMUC + " WHERE " + COLUMN_MA_DANHMUC_THUCHI + " = ? AND " + COLUMN_TEN_DANHMUC + " = ?",
                new String[]{String.valueOf(maDanhmuc), tenDanhmuc});
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    // Get the icon resource ID of a specific danhmuc
    public int getDanhmucIcon(int maDanhmuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        int bieutuong = 0; // Default value

        Cursor cursor = db.query(TABLE_DANHMUC,
                new String[]{COLUMN_BIEUTUONG},
                COLUMN_MA_DANHMUC + " = ?",
                new String[]{String.valueOf(maDanhmuc)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            bieutuong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BIEUTUONG));
            cursor.close();
        }

        return bieutuong;
    }
    // Calculate total amount from thuchi table based on loai
    public double getTotalAmountByLoai(int loai) {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalAmount = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SO_TIEN + ") AS Total FROM " + TABLE_THUCHI +
                " WHERE " + COLUMN_LOAI + " = ?", new String[]{String.valueOf(loai)});

        if (cursor != null && cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("Total"));
            cursor.close();
        }

        return totalAmount;
    }
    // Get all records from thuchi table based on loai
    public Cursor getAllThuchiByLoai(int loai) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(TABLE_THUCHI,
                null,
                COLUMN_LOAI + " = ?",
                new String[]{String.valueOf(loai)},
                null, null, null);
    }
    public Cursor getAllThuchiByMonth(int year, int month) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                COLUMN_MA_DANHMUC_THUCHI,
                COLUMN_SO_TIEN,
                // Add other columns you need
        };
        String selection = "strftime('%Y-%m', " + COLUMN_NGAY_THUCHI + ") = ?";
        String[] selectionArgs = { String.format("%04d-%02d", year, month + 1) }; // SQLite uses 1-based month

        return db.query(TABLE_THUCHI, columns, selection, selectionArgs, null, null, null);
    }
    public double getTotalAmountByMonth(int year, int month) {
        double totalAmount = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { "SUM(" + COLUMN_SO_TIEN + ")" };
        String selection = "strftime('%Y', " + COLUMN_NGAY_THUCHI + ") = ? AND strftime('%m', " + COLUMN_NGAY_THUCHI + ") = ?";
        String[] selectionArgs = { String.valueOf(year), String.format("%02d", month + 1) }; // month + 1 because SQLite month starts from 1

        Cursor cursor = db.query(TABLE_THUCHI, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getDouble(0);
        }

        cursor.close();
        return totalAmount;
    }
    public Cursor getAllThuchiByMonthAndType(int year, int month, int loaiDanhMuc) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { COLUMN_MA_DANHMUC_THUCHI, COLUMN_SO_TIEN };
        String selection = "strftime('%Y', " + COLUMN_NGAY_THUCHI + ") = ? AND strftime('%m', " + COLUMN_NGAY_THUCHI + ") = ? AND " + COLUMN_LOAI + " = ?";
        String[] selectionArgs = { String.valueOf(year), String.format("%02d", month + 1), String.valueOf(loaiDanhMuc) }; // month + 1 because SQLite month starts from 1

        return db.query(TABLE_THUCHI, columns, selection, selectionArgs, null, null, null);
    }

    public double getTotalAmountByMonth(int year, int month, int type) {
        double totalAmount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Ensure correct table name is used (TABLE_THUCHI)
        String query = "SELECT SUM(" + COLUMN_SO_TIEN + ") FROM " + TABLE_THUCHI +
                " WHERE strftime('%Y', " + COLUMN_NGAY_THUCHI + ") = ? AND " +
                " strftime('%m', " + COLUMN_NGAY_THUCHI + ") = ? AND " +
                COLUMN_LOAI + " = ?";

        try {
            cursor = db.rawQuery(query, new String[]{
                    String.valueOf(year),
                    String.format("%02d", month + 1), // SQLite month is 1-based
                    String.valueOf(type)
            });

            if (cursor.moveToFirst()) {
                totalAmount = cursor.getDouble(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return totalAmount;
    }

    // Get all records from thuchi as a List of Strings
    public List<String> getAllThuchiAsStringList() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_GHICHU + ", " + COLUMN_SO_TIEN + " FROM " + TABLE_THUCHI, null);
        if (cursor.moveToFirst()) {
            do {
                // Concatenate the ghi chu and so tien columns
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU)) + " - " +
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_SO_TIEN));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
