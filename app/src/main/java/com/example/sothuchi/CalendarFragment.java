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
    private CustomArrayAdapter adapter;
    private ArrayList<ThuChi> dataList;
    private DatabaseHelper databaseHelper;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //cmt ti
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;

    }

}
