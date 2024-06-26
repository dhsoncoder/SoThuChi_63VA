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

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;


public class CalendarFragment extends Fragment {
    private ListView listView;
    private CustomArrayAdapter adapter;
    private ArrayList<ThuChi> dataList;
    private DatabaseHelper databaseHelper;
    private MaterialCalendarView calendarView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView= view.findViewById(R.id.calendarView);


        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                // Query data for the selected month and year
                dataList = new ArrayList<>(databaseHelper.getThuchiByMonth(calendarView.getCurrentDate().getMonth() , calendarView.getCurrentDate().getYear()));
                adapter = new CustomArrayAdapter(getActivity(), R.layout.item_thu_chi, dataList);
                listView.setAdapter(adapter);
            }
        });

        listView = view.findViewById(R.id.lv_lich); // Replace with your ListView's ID
        databaseHelper = new DatabaseHelper(getContext());

        databaseHelper.insertThuchi(1, 50000, 1, "20/06/2024", "Tiền lương");
        databaseHelper.insertThuchi(2, 20000, 0, "22/12/2023", "Tiền điện");
        databaseHelper.insertThuchi(3, 15000, 0, "2023-03-03", "Tiền nước");

        // Get the current month and year from the MaterialCalendarView
        CalendarDay currentDate = calendarView.getCurrentDate();
        int month = currentDate.getMonth(); // Month is not zero-based
        int year = currentDate.getYear();

        // Query data for the current month and year
        dataList = new ArrayList<>(databaseHelper.getThuchiByMonth(month, year));
        adapter = new CustomArrayAdapter(getActivity(), R.layout.item_thu_chi, dataList);
        listView.setAdapter(adapter);

        return view;

    }

}
