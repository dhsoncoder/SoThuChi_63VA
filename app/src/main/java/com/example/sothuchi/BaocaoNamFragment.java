package com.example.sothuchi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BaocaoNamFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private ColorStateList def;
    private TextView txtthang, txtnam, select, txttongchitieunam, txttongthunhapnam;
    private Button btnchonnam;
    private ImageButton btnlui, btntiep;
    private NumberPicker yearPicker;
    private TabHost tabHost;
    private PieChart pieChartChitieu, pieChartThunhap;
    private RecyclerView recyclerViewChitieu, recyclerViewThunhap ;
    private ChitieuAdapter adapter;
    private ThunhapAdapter adapter2;
    private ArrayList<ThuchiItem> thuchiItems;
    private ArrayList<ThunhapItem> thunhapItems;
    private int currentYear;
    private ScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_baocao_nam, container, false);
        txttongchitieunam = view.findViewById(R.id.txttongchitieunam);
        txttongthunhapnam = view.findViewById(R.id.txttongthunhapnam);
        txtthang = view.findViewById(R.id.txtthang);
        txtnam = view.findViewById(R.id.txtnam);
        select = view.findViewById(R.id.select);
        btnchonnam = view.findViewById(R.id.btnchonnam);
        btnlui = view.findViewById(R.id.btnlui);
        btntiep = view.findViewById(R.id.btntiep);

        scrollView = view.findViewById(R.id.scrollView);

        // Add code to scroll to top when activity starts
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        tabHost = view.findViewById(R.id.Tabhost2);
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

        // Set up listener for tab changes
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // Scroll to top of scrollView when tab changes
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, 0);
                    }
                });
            }
        });
        // Store the default text color
        def = txtnam.getTextColors();

        databaseHelper = new DatabaseHelper(getActivity());

        // Initialize current year to current system date
        final Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);

        // Set initial year on btnchonnam button
        updateYearText();

        // Set click listeners
        txtthang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.animate().x(0).setDuration(100);
                txtthang.setTextColor(Color.WHITE);
                txtnam.setTextColor(def);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BaocaoThangFragment BaocaoThangFragment = new BaocaoThangFragment();
                fragmentTransaction.replace(R.id.frame_container, BaocaoThangFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        txtnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.animate().x(txtnam.getWidth()).setDuration(100);
                txtthang.setTextColor(def);
                txtnam.setTextColor(Color.WHITE);
            }
        });

        btnchonnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearPickerDialog();
            }
        });

        btnlui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentYear--; // Move to previous year
                updateYearText();
                updateUI(); // Update UI based on new selected year
            }
        });

        btntiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentYear++; // Move to next year
                updateYearText();
                updateUI(); // Update UI based on new selected year
            }
        });

        // Initialize views
        pieChartChitieu = view.findViewById(R.id.chartchitieu);
        recyclerViewChitieu = view.findViewById(R.id.recyclerviewchitieu);
        recyclerViewChitieu.setLayoutManager(new LinearLayoutManager(getActivity()));

        pieChartThunhap = view.findViewById(R.id.chartthunhap);
        recyclerViewThunhap = view.findViewById(R.id.recyclerviewthunhap);
        recyclerViewThunhap.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize ArrayList and Adapter
        thuchiItems = new ArrayList<>();
        thunhapItems = new ArrayList<>();
        adapter2 = new ThunhapAdapter(getActivity(), thunhapItems);
        adapter = new ChitieuAdapter(getActivity(), thuchiItems);
        recyclerViewChitieu.setAdapter(adapter);
        recyclerViewThunhap.setAdapter(adapter2);

        // Update UI components
        updateYearText(); // Update the initial text of btnchonthang
        updateUI(); // Update UI based on initial selected month

        return view;

    }

    private void updateYearText() {
        String selectedDate = "Năm " + currentYear;
        btnchonnam.setText(selectedDate);
    }

    private void showYearPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chọn năm");

        yearPicker = new NumberPicker(getActivity());
        int yearRangeStart = 1900; // Change this if you want to adjust the start of the range
        int yearRangeEnd = 2100;   // Change this if you want to adjust the end of the range
        String[] displayValues = generateYears(yearRangeStart, yearRangeEnd);
        yearPicker.setMinValue(0);
        yearPicker.setMaxValue(displayValues.length - 1);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setDisplayedValues(displayValues);
        yearPicker.setValue(currentYear - yearRangeStart); // Set initial value to current year

        builder.setView(yearPicker);

        builder.setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentYear = yearPicker.getValue() + yearRangeStart;
                updateYearText();
                updateUI(); // Update UI based on new selected year
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String[] generateYears(int startYear, int endYear) {
        int numYears = endYear - startYear + 1;
        String[] years = new String[numYears];
        for (int i = 0; i < numYears; i++) {
            years[i] = String.valueOf(startYear + i);
        }
        return years;
    }

    private void updateUI() {
        updateTabChiTieu(); // Update Chi tiêu tab
        updateTabThuNhap(); // Update Thu nhập tab
        updateTotalAmounts();
    }
    private void updateTabChiTieu() {
        updatePieChartChitieu();
        updateRecyclerViewChitieu();
    }

    private void updateTabThuNhap() {
        updatePieChartThunhap();
        updateRecyclerViewThunhap();
    }

    private void updateTotalAmounts() {
        double totalChitieu = databaseHelper.getTotalAmountByYear(currentYear); // Type 0 for expense
        double totalThunhap = databaseHelper.getTotalAmountByYear(currentYear); // Type 1 for income

        txttongchitieunam.setText(String.format("%+,.0f", totalChitieu));
        txttongthunhapnam.setText(String.format("%+,.0f", totalThunhap));
    }
    private void updatePieChartChitieu() {
        List<GroupedThuchiItem> groupedEntries = new ArrayList<>();
        double totalAmount = databaseHelper.getTotalAmountByYear(currentYear);

        Cursor cursor = databaseHelper.getAllThuchiByYearAndType(currentYear, 0);

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc);
                int bieutuong = databaseHelper.getDanhmucIcon(maDanhmuc);

                // Kiểm tra xem nhóm danh mục đã tồn tại trong groupedEntries chưa
                boolean found = false;
                for (GroupedThuchiItem item : groupedEntries) {
                    if (item.getTenDanhmuc().equals(tenDanhmuc) && item.getMauSac().equals(mauSac) && item.getBieutuong() == bieutuong) {
                        item.addSoTien(soTien);
                        found = true;
                        break;
                    }
                }

                // Nếu chưa tồn tại, thêm vào groupedEntries
                if (!found) {
                    groupedEntries.add(new GroupedThuchiItem(tenDanhmuc, mauSac, bieutuong, soTien));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Tạo danh sách PieEntry từ groupedEntries
        List<PieEntry> entries = new ArrayList<>();
        for (GroupedThuchiItem item : groupedEntries) {
            float percentage = (float) ((item.getSoTien() / totalAmount) * 100);
            entries.add(new PieEntry(percentage, item.getTenDanhmuc()));
        }

        // Thiết lập màu sắc cho từng PieEntry
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            int color = Color.parseColor(groupedEntries.get(i).getMauSac());
            colors.add(color);
        }

        // Tạo PieDataSet và thiết lập dữ liệu
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);

        // Tạo PieData và thiết lập vào PieChart
        PieData pieData = new PieData(dataSet);
        pieChartChitieu.setData(pieData);
        pieChartChitieu.invalidate();
    }

    private void updateRecyclerViewChitieu() {
        thuchiItems.clear();
        double totalAmount = databaseHelper.getTotalAmountByYear(currentYear);
        Cursor cursor = databaseHelper.getAllThuchiByYearAndType(currentYear, 0);

        List<GroupedThuchiItem> groupedEntries = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc);
                int bieutuong = databaseHelper.getDanhmucIcon(maDanhmuc);

                // Kiểm tra xem nhóm danh mục đã tồn tại trong groupedEntries chưa
                boolean found = false;
                for (GroupedThuchiItem item : groupedEntries) {
                    if (item.getTenDanhmuc().equals(tenDanhmuc) && item.getMauSac().equals(mauSac) && item.getBieutuong() == bieutuong) {
                        item.addSoTien(soTien);
                        found = true;
                        break;
                    }
                }

                // Nếu chưa tồn tại, thêm vào groupedEntries
                if (!found) {
                    groupedEntries.add(new GroupedThuchiItem(tenDanhmuc, mauSac, bieutuong, soTien));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Tạo danh sách ThuchiItem từ groupedEntries
        for (GroupedThuchiItem item : groupedEntries) {
            float percentage = (float) ((item.getSoTien() / totalAmount) * 100);
            ThuchiItem thuchiItem = new ThuchiItem(item.getTenDanhmuc(), percentage, 1, item.getSoTien(), item.getBieutuong(), item.getMauSac());
            thuchiItems.add(thuchiItem);
        }

        adapter.notifyDataSetChanged();

        // Hiển thị thông báo nếu không có dữ liệu cho tháng được chọn
        if (thuchiItems.isEmpty()) {
            Toast.makeText(getActivity(), "Không có dữ liệu cho năm này", Toast.LENGTH_SHORT).show();
        }
    }




    private void updatePieChartThunhap() {
        List<GroupedThunhapItem> groupedEntries = new ArrayList<>();
        double totalAmount = databaseHelper.getTotalAmountByYear(currentYear); // Type 1 for income

        Cursor cursor = databaseHelper.getAllThuchiByYearAndType(currentYear, 1); // Type 1 for income

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc);
                int bieutuong = databaseHelper.getDanhmucIcon(maDanhmuc);

                // Check if the category is already in groupedEntries
                boolean found = false;
                for (GroupedThunhapItem item : groupedEntries) {
                    if (item.getTenDanhmuc().equals(tenDanhmuc) && item.getMauSac().equals(mauSac) && item.getBieutuong() == bieutuong) {
                        item.addSoTien(soTien);
                        found = true;
                        break;
                    }
                }

                // If not found, add a new entry to groupedEntries
                if (!found) {
                    groupedEntries.add(new GroupedThunhapItem(tenDanhmuc, mauSac, bieutuong, soTien));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Create PieEntries with correct percentage based on totalAmount
        List<PieEntry> entries = new ArrayList<>();
        for (GroupedThunhapItem item : groupedEntries) {
            float percentage = (float) ((item.getSoTien() / totalAmount) * 100);
            entries.add(new PieEntry(percentage, item.getTenDanhmuc()));
        }

        // Set colors for each PieEntry
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            int color = Color.parseColor(groupedEntries.get(i).getMauSac());
            colors.add(color);
        }

        // Create PieDataSet and set data
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);

        // Create PieData and set to PieChart
        PieData pieData = new PieData(dataSet);
        pieChartThunhap.setData(pieData);
        pieChartThunhap.invalidate();
    }



    private void updateRecyclerViewThunhap() {
        thunhapItems.clear();
        double totalAmount = databaseHelper.getTotalAmountByYear(currentYear); // Type 1 for income
        Cursor cursor = databaseHelper.getAllThuchiByYearAndType(currentYear, 1); // Type 1 for income

        List<GroupedThunhapItem> groupedEntries = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int maDanhmuc = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC_THUCHI));
                double soTien = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_TIEN));
                String tenDanhmuc = databaseHelper.getDanhmucName(maDanhmuc);
                String mauSac = databaseHelper.getDanhmucColor(maDanhmuc);
                int bieutuong = databaseHelper.getDanhmucIcon(maDanhmuc);

                // Check if the category is already in groupedEntries
                boolean found = false;
                for (GroupedThunhapItem item : groupedEntries) {
                    if (item.getTenDanhmuc().equals(tenDanhmuc) && item.getMauSac().equals(mauSac) && item.getBieutuong() == bieutuong) {
                        item.addSoTien(soTien);
                        found = true;
                        break;
                    }
                }

                // If not found, add a new entry to groupedEntries
                if (!found) {
                    groupedEntries.add(new GroupedThunhapItem(tenDanhmuc, mauSac, bieutuong, soTien));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Create ThunhapItem list from groupedEntries
        for (GroupedThunhapItem item : groupedEntries) {
            float percentage = (float) ((item.getSoTien() / totalAmount) * 100);
            ThunhapItem thunhapItem = new ThunhapItem(item.getTenDanhmuc(), percentage, 1, item.getSoTien(), item.getBieutuong(), item.getMauSac());
            thunhapItems.add(thunhapItem);
        }

        adapter.notifyDataSetChanged();

        // Show a message if no data is available for the selected month
        if (thunhapItems.isEmpty()) {
            Toast.makeText(getActivity(), "Không có dữ liệu cho năm này", Toast.LENGTH_SHORT).show();
        }
    }
}

