package com.example.sothuchi;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView recyclerView;
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
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ArrayList and Adapter
        thuchiItems = new ArrayList<>();
        adapter = new CustomAdapter(this, thuchiItems);
        recyclerView.setAdapter(adapter);

        // Update UI components
        updateUI();
    }

    private void updateUI() {
        updatePieChart();
        updateRecyclerView();
    }

    private void updatePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        double totalAmount = databaseHelper.getTotalAmount();
        Cursor cursor = databaseHelper.getAllThuchi();

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc);

                float percentage = (float) (soTien / totalAmount) * 100;

                entries.add(new PieEntry(percentage, tenDanhmuc));
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

    private void updateRecyclerView() {
        thuchiItems.clear();
        double totalAmount = databaseHelper.getTotalAmount();
        Cursor cursor = databaseHelper.getAllThuchi();

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc);
                int bieutuong = databaseHelper.getDanhmucIcon(maDanhmuc);

                float percentage = (float) (soTien / totalAmount) * 100;

                // Check if the item with the same icon and category name already exists
                boolean exists = false;
                for (ThuchiItem item : thuchiItems) {
                    if (item.getTenDanhmuc().equals(tenDanhmuc) && item.getBieuTuong() == bieutuong) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    // If not exists, count occurrences and add to list
                    int count = databaseHelper.getCountByDanhmuc(maDanhmuc, tenDanhmuc);
                    ThuchiItem item = new ThuchiItem(tenDanhmuc, percentage, count, soTien, bieutuong, mauSac);
                    thuchiItems.add(item);
                }
            } while (cursor.moveToNext());
        }

        adapter.notifyDataSetChanged();
        cursor.close();
    }
}