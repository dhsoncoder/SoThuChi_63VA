package com.example.sothuchi;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TienChiFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private GridView gridViewDanhmuc;
    private DanhmucAdapter adapter;

    TextView calendarText;
    ImageView imgLeft, imgRight;
    EditText edtExpense, edtNote;
    Button btnExpense;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tienchi, container, false);
        gridViewDanhmuc = view.findViewById(R.id.gridViewDanhmuc);
        databaseHelper = new DatabaseHelper(getContext());

        // Thêm dữ liệu mẫu
        databaseHelper.insertSampleData();

        // Lấy dữ liệu từ database
        Cursor cursor = databaseHelper.getDanhmucByLoai(1);

        // Log cursor count
        Log.d("TienChiFragment", "onCreateView: Cursor count = " + cursor.getCount());

        // Tạo adapter và thiết lập cho GridView
        adapter = new DanhmucAdapter(getContext(), cursor);
        gridViewDanhmuc.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarText = view.findViewById(R.id.edt_calender);
        imgLeft = view.findViewById(R.id.imgLeft);
        imgRight = view.findViewById(R.id.imgRight);
        edtExpense = view.findViewById(R.id.edtExpense);
        edtNote = view.findViewById(R.id.edtNote);
        btnExpense = view.findViewById(R.id.btnExpense);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Chọn ngày");
        final MaterialDatePicker materialDatePicker = builder.build();

        calendarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // Convert the selection to a Long
                Long selectedDate = (Long) selection;

                // Create a new Date object from the selectedDate
                Date date = new Date(selectedDate);

                // Create a SimpleDateFormat to format the Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                // Set the text of calendarText to the formatted date
                calendarText.setText(dateFormat.format(date));
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

        // Clear text of edtExpense on focus and reset to "0.00" on focus loss if empty
        edtExpense.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (edtExpense.getText().toString().equals("0.00")) {
                        edtExpense.setText("");
                    }
                } else {
                    if (edtExpense.getText().toString().isEmpty()) {
                        edtExpense.setText("0.00");
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

        // Set item click listener for GridView
        gridViewDanhmuc.setOnItemClickListener((parent, view1, position, id) -> {
            adapter.setSelectedPosition(position);
        });

        // Set onClick listener for btnExpense
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate = calendarText.getText().toString();
                String expenseText = edtExpense.getText().toString();
                String noteText = edtNote.getText().toString();
                int selectedPosition = adapter.getSelectedPosition();

                if (selectedPosition == -1) {
                    Toast.makeText(getContext(), "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(selectedDate) || TextUtils.isEmpty(expenseText) || "0.00".equals(expenseText)) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int maDanhmuc = (int) adapter.getItemId(selectedPosition);
                double soTien = Double.parseDouble(expenseText);

                // Lưu dữ liệu vào cơ sở dữ liệu
                long result = databaseHelper.insertThuchi(maDanhmuc, soTien, 1, selectedDate, noteText);
                if (result != -1) {
                    Toast.makeText(getContext(), "Thêm khoản chi thành công", Toast.LENGTH_SHORT).show();
                    // Xóa dữ liệu sau khi lưu thành công
                    edtExpense.setText("0.00");
                    edtNote.setText("Trống");
                    adapter.setSelectedPosition(-1);
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
