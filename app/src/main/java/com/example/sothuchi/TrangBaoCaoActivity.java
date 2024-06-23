package com.example.sothuchi;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TrangBaoCaoActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    ColorStateList def;
    TextView txtthang, txtnam, select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_bao_cao);

        txtthang = findViewById(R.id.txtthang);
        txtnam = findViewById(R.id.txtnam);
        select = findViewById(R.id.select);

        // Store the default text color
        def = txtnam.getTextColors();

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Insert and query data
        insertAndQueryData();

        // Set up click listeners
        txtthang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.animate().x(0).setDuration(100);
                txtthang.setTextColor(Color.WHITE);
                txtnam.setTextColor(def);
            }
        });

        txtnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.animate().x(txtnam.getWidth()).setDuration(100);
                txtthang.setTextColor(def);
                txtnam.setTextColor(Color.WHITE);

                Intent intent = new Intent(TrangBaoCaoActivity.this, TrangBaoCaoNamActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertAndQueryData() {
//        // Insert a record into the danhmuc table
//        long danhmucId = databaseHelper.insertDanhmuc("Thực phẩm", 1, R.drawable.cake, "#FF0000");
//        if (danhmucId != -1) {
//            Log.d("TrangBieuDo", "Record inserted into danhmuc table with id: " + danhmucId);
//        } else {
//            Log.d("TrangBieuDo", "Failed to insert record into danhmuc table");
//            return;
//        }
//
//        // Insert a record into the thuchi table
//        long thuchiId = databaseHelper.insertThuchi((int) danhmucId, 50000.0, 1, "2023-06-21", "Chi phí bữa trưa");
//        if (thuchiId != -1) {
//            Log.d("TrangBieuDo", "Record inserted into thuchi table with id: " + thuchiId);
//        } else {
//            Log.d("TrangBieuDo", "Failed to insert record into thuchi table");
//        }
//
//        // Query the danhmuc table to verify the record was inserted
//        Cursor danhmucCursor = databaseHelper.getAllDanhmuc();
//        if (danhmucCursor.moveToFirst()) {
//            do {
//                int maDanhmuc = danhmucCursor.getInt(danhmucCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC));
//                String tenDanhmuc = danhmucCursor.getString(danhmucCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEN_DANHMUC));
//                int loai = danhmucCursor.getInt(danhmucCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOAI));
//                int bieutuong = danhmucCursor.getInt(danhmucCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIEUTUONG));
//                String mausac = danhmucCursor.getString(danhmucCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MAUSAC));
//
//                Log.d("TrangBieuDo", "Danhmuc - ID: " + maDanhmuc + ", Name: " + tenDanhmuc + ", Type: " + loai +
//                        ", Icon: " + bieutuong + ", Color: " + mausac);
//            } while (danhmucCursor.moveToNext());
//        }
//        danhmucCursor.close();
//
//        // Query the thuchi table to verify the record was inserted
//        Cursor thuchiCursor = databaseHelper.getAllThuchi();
//        if (thuchiCursor.moveToFirst()) {
//            do {
//                int maThuchi = thuchiCursor.getInt(thuchiCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_THUCHI));
//                int maDanhmucThuchi = thuchiCursor.getInt(thuchiCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
//                double soTien = thuchiCursor.getDouble(thuchiCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
//                int loai = thuchiCursor.getInt(thuchiCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOAI));
//                String ngayThuchi = thuchiCursor.getString(thuchiCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAY_THUCHI));
//                String ghichu = thuchiCursor.getString(thuchiCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GHICHU));
//
//                Log.d("TrangBieuDo", "Thuchi - ID: " + maThuchi + ", Danhmuc ID: " + maDanhmucThuchi + ", Amount: " + soTien +
//                        ", Type: " + loai + ", Date: " + ngayThuchi + ", Note: " + ghichu);
//            } while (thuchiCursor.moveToNext());
//        }
//        thuchiCursor.close();
    }
}
