package com.example.sothuchi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    // Get all DanhMuc by Loai
    public List<DanhMuc> getAllDanhMucByLoai(int loai) {
        List<DanhMuc> danhMucList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_MA_DANHMUC,
                COLUMN_TEN_DANHMUC,
                COLUMN_BIEUTUONG,
                COLUMN_MAUSAC
        };
        String selection = COLUMN_LOAI + " = ?";
        String[] selectionArgs = { String.valueOf(loai) };

        Cursor cursor = db.query(TABLE_DANHMUC, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maDanhMuc = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_DANHMUC));
                String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_DANHMUC));
                int bieuTuong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BIEUTUONG));
                String mauSac = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAUSAC));

                // Kiểm tra xem danh mục này đã có trong danh sách chưa
                boolean exists = false;
                for (DanhMuc danhMuc : danhMucList) {
                    if (danhMuc.getTenDanhMuc().equals(tenDanhMuc) &&
                            danhMuc.getBieuTuong() == bieuTuong &&
                            danhMuc.getMauSac().equals(mauSac)) {
                        exists = true;
                        break;
                    }
                }

                // Nếu chưa tồn tại, thêm vào danh sách
                if (!exists) {
                    DanhMuc danhMuc = new DanhMuc();
                    danhMuc.setMaDanhMuc(maDanhMuc);
                    danhMuc.setTenDanhMuc(tenDanhMuc);
                    danhMuc.setBieuTuong(bieuTuong);
                    danhMuc.setMauSac(mauSac);

                    danhMucList.add(danhMuc);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return danhMucList;
    }

    // Delete a DanhMuc by ID
    public void deleteDanhMuc(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xác nhận xóa trước khi thực hiện
        try {
            db.beginTransaction();
            // Thực hiện xóa
            db.delete(TABLE_DANHMUC, COLUMN_MA_DANHMUC + " = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
