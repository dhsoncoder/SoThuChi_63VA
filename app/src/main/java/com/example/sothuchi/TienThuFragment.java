package com.example.sothuchi;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TienThuFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private GridView gridViewDanhmuc;

    TextView calendarText;
    ImageView imgLeft, imgRight;
    EditText edtIncome, edtNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tienthu, container, false);
        gridViewDanhmuc = view.findViewById(R.id.gridViewDanhmuc);
        databaseHelper = new DatabaseHelper(getContext());

        // Lấy dữ liệu từ database
        Cursor cursor = databaseHelper.getDanhmucByLoai(0);

        // Tạo adapter và thiết lập cho GridView
        DanhmucAdapter adapter = new DanhmucAdapter(getContext(), cursor);
        gridViewDanhmuc.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarText = view.findViewById(R.id.edt_calender);
        imgLeft = view.findViewById(R.id.imgLeft);
        imgRight = view.findViewById(R.id.imgRight);
        edtIncome = view.findViewById(R.id.edtIncome);
        edtNote = view.findViewById(R.id.edtNote);

        // Khởi tạo và thiết lập DatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Chọn ngày");
        final MaterialDatePicker materialDatePicker = builder.build();

        calendarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                materialDatePicker .show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }

        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object Selection) {
                calendarText.setText(materialDatePicker.getHeaderText());
            }
        });

        // Thiết lập ngày hiện tại cho calendarText
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        calendarText.setText(currentDate);


        // Xử lí sự kiện cho imgLeft và imgRight
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                String newDate = dateFormat.format(calendar.getTime());
                calendarText.setText(newDate);
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String newDate = dateFormat.format(calendar.getTime());
                calendarText.setText(newDate);
            }
        });

        // Clear text of edtIncome on focus and reset to "0.00" on focus loss if empty
        edtIncome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (edtIncome.getText().toString().equals("0.00")) {
                        edtIncome.setText("");
                    }
                } else {
                    if (edtIncome.getText().toString().isEmpty()) {
                        edtIncome.setText("0.00");
                    }
                }
            }
        });

        // Set hint behavior for edtNote
        edtNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && edtNote.getText().toString().equals("Trống")) {
                    edtNote.setText("");
                } else if (!hasFocus && edtNote.getText().toString().isEmpty()) {
                    edtNote.setText("Trống");
                }
            }
        });
    }
}