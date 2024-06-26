package com.example.sothuchi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditExpenses extends AppCompatActivity {
    TextView calendarText;
    ImageView imgLeft, imgRight;
    EditText edtExpense,edtIncome, edtNote;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_expenses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize the TextViews and EditTexts
        calendarText = findViewById(R.id.edt_calender);
        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        edtIncome = findViewById(R.id.edtIncome);
        edtNote = findViewById(R.id.edtNote);
        button = findViewById(R.id.button);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        final MaterialDatePicker materialDatePicker = builder.build();
        calendarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the date picker
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
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


        // Set onClick listeners for imgLeft and imgRight
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
                if (hasFocus && edtNote.getText().toString().equals("Not entered")) {
                    edtNote.setText("");
                } else if (!hasFocus && edtNote.getText().toString().isEmpty()) {
                    edtNote.setText("Not entered");
                }
            }
        });

        // Get the ID from the Intent
        int itemId = getIntent().getIntExtra("item_id", -1);

        // Query the database to get the ThuChi item
        // Replace DatabaseHelper with your actual database helper class
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ThuChi item = databaseHelper.getThuChiById(itemId);

        // Display the information of the ThuChi item
        calendarText.setText(item.getNgayThang());
        edtNote.setText(item.getGhiChu());
        edtIncome.setText(String.format("%,.0f",item.getSoTien()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new values from the EditTexts
                String newDate = calendarText.getText().toString();
                String newNote = edtNote.getText().toString();
                double newIncome = Double.parseDouble(edtIncome.getText().toString().replace("đ", "").replace(",", ""));

                // Update the ThuChi item in the database
                databaseHelper.updateThuchi(itemId,item.getIdDanhMuc(), newIncome, 0, newDate, newNote);

                // Finish the activity
                finish();
            }
        });
    }
    }

