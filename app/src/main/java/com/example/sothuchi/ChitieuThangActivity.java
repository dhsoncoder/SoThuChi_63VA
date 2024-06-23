package com.example.sothuchi;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChitieuThangActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private PieChart pieChart;
    private ListView listView;
    private CustomAdapter adapter;
    private ArrayList<ThuchiItem> thuchiItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitieu_thang);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        pieChart = findViewById(R.id.chart);
        listView = findViewById(R.id.listview1);

        // Initialize ArrayList and Adapter
        thuchiItems = new ArrayList<>();
        adapter = new CustomAdapter(this, R.layout.list_baocao, thuchiItems);
        listView.setAdapter(adapter);

        // Update UI components
        updateUI();
    }

    private void updateUI() {
        updatePieChart();
        updateListView();
    }

    private void updatePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllThuchi();

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc); // Get color from database

                int color = Color.parseColor(mauSac); // Parse color from string

                entries.add(new PieEntry((float) soTien, tenDanhmuc, color));
            } while (cursor.moveToNext());
        }

        PieDataSet dataSet = new PieDataSet(entries, "Thuchi Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set a default color palette
        dataSet.setValueTextColor(Color.WHITE); // Set text color for data values
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        cursor.close();
    }

    private void updateListView() {
        thuchiItems.clear(); // Clear the ArrayList to avoid duplications
        Cursor cursor = databaseHelper.getAllThuchi();

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc); // Get color from database

                // Create ThuchiItem object and add to list for ListView
                ThuchiItem item = new ThuchiItem();
                item.setTenDanhmuc(tenDanhmuc);
                item.setSoTien(soTien);
                item.setBieuTuong(R.drawable.cake); // Set your icon here
                item.setMauSac(mauSac); // Set color from SQLite
                thuchiItems.add(item);
            } while (cursor.moveToNext());
        }

        // Notify the adapter that data set has changed
        adapter.notifyDataSetChanged();

        cursor.close();
    }
}
