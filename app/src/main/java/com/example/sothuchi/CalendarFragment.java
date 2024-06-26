package com.example.sothuchi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class CalendarFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
private  DatabaseHelper databaseHelper;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getContext(), "Selected Date: " + date, Toast.LENGTH_SHORT).show();
            }
        });
        listView = view.findViewById(R.id.lv_lich); // Replace with your ListView's ID
        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.insertThuchi(1, 50000, 1, "2023-03-01", "Tiền lương");
        databaseHelper.insertThuchi(2, 20000, 0, "2023-03-02", "Tiền điện");
        databaseHelper.insertThuchi(3, 15000, 0, "2023-03-03", "Tiền nước");

        dataList = new ArrayList<>(databaseHelper.getAllThuchiAsStringList());
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        dataList.clear();
        dataList.addAll(databaseHelper.getAllThuchiAsStringList());
        adapter.notifyDataSetChanged();
        return view;

    }
}