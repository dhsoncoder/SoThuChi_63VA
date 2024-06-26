package com.example.sothuchi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public Cursor getAllThuchiByYearAndType(int year, int type) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { COLUMN_MA_DANHMUC_THUCHI, COLUMN_SO_TIEN };
        String selection = "strftime('%Y', " + COLUMN_NGAY_THUCHI + ") = ? AND " + COLUMN_LOAI + " = ?";
        String[] selectionArgs = { String.valueOf(year), String.valueOf(type) };

        return db.query(TABLE_THUCHI, columns, selection, selectionArgs, null, null, null);
    }
    public double getTotalAmountByYear(int year) {
        double totalAmount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT SUM(" + COLUMN_SO_TIEN + ") FROM " + TABLE_THUCHI +
                " WHERE strftime('%Y', " + COLUMN_NGAY_THUCHI + ") = ?";

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(year)});

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

    // Get all records from danhmuc where loai is 1
    public Cursor getDanhmucByLoai(int loai) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_DANHMUC,
                null,
                COLUMN_LOAI + " = ?",
                new String[]{String.valueOf(loai)},
                null,
                null,
                null);
    }
    // Get all records from thuchi as a List of Strings
    public List<ThuChi> getThuchiByMonth(int month, int year) {
        List<ThuChi> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THUCHI + " WHERE strftime('%m', substr(" + COLUMN_NGAY_THUCHI + ", 7, 4) || '-' || substr(" + COLUMN_NGAY_THUCHI + ", 4, 2) || '-' || substr(" + COLUMN_NGAY_THUCHI + ", 1, 2)) = ? AND strftime('%Y', substr(" + COLUMN_NGAY_THUCHI + ", 7, 4) || '-' || substr(" + COLUMN_NGAY_THUCHI + ", 4, 2) || '-' || substr(" + COLUMN_NGAY_THUCHI + ", 1, 2)) = ?", new String[]{String.format("%02d", month), String.valueOf(year)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_SO_TIEN));
                int loai = cursor.getInt(cursor.getColumnIndex(COLUMN_LOAI));
                String ngayThang = cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_THUCHI));
                String ghiChu = cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU));
                int id_dm = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_DANHMUC));
                list.add(new ThuChi(id, soTien, loai, ngayThang, ghiChu,id_dm));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public ArrayList<ThuChi> getThuchiByDate(String date) {
        ArrayList<ThuChi> thuChiList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE ngay_thuchi = ?", new String[]{date});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_SO_TIEN));
                int loai = cursor.getInt(cursor.getColumnIndex(COLUMN_LOAI));
                String ngayThang = cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_THUCHI));
                String ghiChu = cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU));
                int id_dm = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_DANHMUC));
                thuChiList.add(new ThuChi(id, soTien, loai, ngayThang, ghiChu,id_dm));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return thuChiList;
}

    public ThuChi getThuChiById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_THUCHI,
                null,
                COLUMN_MA_THUCHI + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int maThuchi = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_THUCHI));
            double soTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_SO_TIEN));
            int loai = cursor.getInt(cursor.getColumnIndex(COLUMN_LOAI));
            String ngayThuchi = cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_THUCHI));
            String ghiChu = cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU));
            int id_dm= cursor.getInt(cursor.getColumnIndex(COLUMN_MA_DANHMUC));
            cursor.close();

            return new ThuChi(maThuchi, soTien, loai, ngayThuchi, ghiChu,id_dm);
        }

        return null;
    }
    public void insertSampleData() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Clear existing data
        db.delete(TABLE_DANHMUC, null, null);
        db.delete(TABLE_THUCHI, null, null);

        // Insert sample categories (danhmuc)
        long result1 = insertDanhmuc("Ăn uống", 1, R.drawable.ic_food, "#FF0000");
        long result2 = insertDanhmuc("Giải trí", 1, R.drawable.ic_next, "#00FF00");
        long result3 = insertDanhmuc("Y tế", 1, R.drawable.ic_calendar, "#0000FF");

        long result4 = insertDanhmuc("Tiền lương", 0, R.drawable.ic_food, "#FF0000");
        long result5 = insertDanhmuc("Làm thêm", 0, R.drawable.ic_next, "#00FF00");
        long result6 = insertDanhmuc("Thưởng", 0, R.drawable.ic_calendar, "#0000FF");

        // Log the result to check if data is inserted correctly
        Log.d("DatabaseHelper", "insertSampleData: " + result1 + ", " + result2 + ", " + result3 + ", " + result4 + ", " + result5 + ", " + result6);

        // Insert sample expenses (thuchi)
        insertThuchi(1, 100000, 1, "26/06/2024", "Ăn uống");
        insertThuchi(2, 150000, 1, "25/06/2024", "Xem phim");
        insertThuchi(3, 200000, 1, "24/06/2024", "Thuốc");

        insertThuchi(4, 100000, 0, "26/06/2024", "Tiền lương");
        insertThuchi(5, 150000, 0, "25/06/2024", "Làm thêm");
        insertThuchi(6, 200000, 0, "24/06/2024", "Thưởng");
    }
    // Get all records from danhmuc where loai 1
    public Cursor getDanhmucLoai1() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_DANHMUC, null, COLUMN_LOAI + " = ?", new String[]{"1"}, null, null, null);
    }

    // Get all records from danhmuc where loai 0
    public Cursor getDanhmucLoai0() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_DANHMUC, null, COLUMN_LOAI + " = ?", new String[]{"0"}, null, null, null);
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
