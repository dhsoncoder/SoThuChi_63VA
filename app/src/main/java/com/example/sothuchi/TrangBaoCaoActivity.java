package com.example.sothuchi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TrangBaoCaoActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ColorStateList def;
    private TextView txtthang, txtnam, select;
    private Button btnchonthang;
    private ImageButton btnlui, btntiep;
    private TabHost tabHost;

    private PieChart pieChart;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<ThuchiItem> thuchiItems;

    private int currentYear;
    private int currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_bao_cao);

        txtthang = findViewById(R.id.txtthang);
        txtnam = findViewById(R.id.txtnam);
        select = findViewById(R.id.select);
        btnchonthang = findViewById(R.id.btnchonthang);
        btnlui = findViewById(R.id.btnlui);
        btntiep = findViewById(R.id.btntiep);

        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        // Create tab Chi tiêu
        TabHost.TabSpec tabct = tabHost.newTabSpec("chitieu");
        tabct.setContent(R.id.tabchitieu);
        tabct.setIndicator("Chi tiêu");
        tabHost.addTab(tabct);

        // Create tab Thu nhập
        TabHost.TabSpec tabtn = tabHost.newTabSpec("thunhap");
        tabtn.setContent(R.id.tabthunhap);
        tabtn.setIndicator("Thu nhập");
        tabHost.addTab(tabtn);

        // Store the default text color
        def = txtnam.getTextColors();

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize current month and year to current system date
        final Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);

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

        btnchonthang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        btnlui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMonth == 0) { // If current month is January (0 index)
                    currentMonth = 11; // Set to December
                    currentYear--; // Move to previous year
                } else {
                    currentMonth--; // Decrease month by 1
                }
                updateMonthText();
                updateUI(); // Update UI based on new selected month
            }
        });

        btntiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMonth == 11) { // If current month is December
                    currentMonth = 0; // Set to January
                    currentYear++; // Move to next year
                } else {
                    currentMonth++; // Increase month by 1
                }
                updateMonthText();
                updateUI(); // Update UI based on new selected month
            }
        });

        // Initialize views
        pieChart = findViewById(R.id.chart);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ArrayList and Adapter
        thuchiItems = new ArrayList<>();
        adapter = new CustomAdapter(this, thuchiItems);
        recyclerView.setAdapter(adapter);

        // Update UI components
        updateMonthText(); // Update the initial text of btnchonthang
        updateUI(); // Update UI based on initial selected month
    }

    private void updateMonthText() {
        String selectedDate = "Tháng " + (currentMonth + 1) + " Năm " + currentYear;
        btnchonthang.setText(selectedDate);
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

    private void showDatePicker() {
        int year = currentYear;
        int month = currentMonth;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TrangBaoCaoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        currentYear = year;
                        currentMonth = monthOfYear;
                        updateMonthText();
                        updateUI(); // Update UI based on new selected month
                    }
                }, year, month, 1); // Day is set to 1 (arbitrary as we only care about month and year)

        datePickerDialog.show();
    }

    private void insertAndQueryData() {
        // Database operations (insert and query) here
    }
}
