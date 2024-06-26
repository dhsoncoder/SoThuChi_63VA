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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;


public class CalendarFragment extends Fragment {
    private ListView listView;
    private TextView tvThuNhap, tvChiTieu, tvTong;
    private CustomArrayAdapter adapter;
    private ArrayList<ThuChi> dataList;
    private DatabaseHelper databaseHelper;
    private MaterialCalendarView calendarView;
    private SearchView searchView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        tvThuNhap = view.findViewById(R.id.tvThuNhap);
        tvChiTieu = view.findViewById(R.id.tvChiTieu);
        tvTong = view.findViewById(R.id.tvTong);
        calendarView= view.findViewById(R.id.calendarView);
        searchView = view.findViewById(R.id.searchbtn);


        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
              resetUI();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String dateString = String.format("%02d/%02d/%04d", date.getDay(), date.getMonth(), date.getYear());
                dataList = new ArrayList<>(databaseHelper.getThuchiByDate(dateString));
                adapter = new CustomArrayAdapter(getActivity(), R.layout.item_thu_chi, dataList);
                listView.setAdapter(adapter);
            }
        });
        listView = view.findViewById(R.id.lv_lich); // Replace with your ListView's ID
        databaseHelper = new DatabaseHelper(getContext());

        databaseHelper.insertThuchi(1, 0, 1, "20/06/2024", "Tiền lương");
        databaseHelper.insertThuchi(2, 20000, 0, "23/06/2024", "Tiền điện");
        databaseHelper.insertThuchi(3, 15000, 0, "2023-03-03", "Tiền nước");

      resetUI();

        return view;

    }
public void resetUI(){
    // Query data for the selected month and year
    dataList = new ArrayList<>(databaseHelper.getThuchiByMonth(calendarView.getCurrentDate().getMonth() , calendarView.getCurrentDate().getYear()));
    adapter = new CustomArrayAdapter(getActivity(), R.layout.item_thu_chi, dataList);
    listView.setAdapter(adapter);


    // Calculate the total income, expense and total
    double totalIncome = 0, totalExpense = 0;
    for (ThuChi thuChi : dataList) {
        if (thuChi.getLoai() == 1) {
            totalIncome += thuChi.getSoTien();
        } else {
            totalExpense += thuChi.getSoTien();
        }
    }
    double total = totalIncome - totalExpense;

    // Update the TextViews
    tvThuNhap.setText(String.format("%,.0fđ", totalIncome));
    tvChiTieu.setText(String.format("%,.0fđ", totalExpense));
    tvTong.setText(String.format("%,.0fđ", total));
}
    private void performSearch(String query) {
        if (dataList != null) {
            ArrayList<ThuChi> filteredList = new ArrayList<>();
            for (ThuChi item : dataList) {
                if (item.getGhiChu().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            adapter = new CustomArrayAdapter(getActivity(), R.layout.item_thu_chi, filteredList);
            listView.setAdapter(adapter);
        }
    }
}
