package com.example.sothuchi;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrangBaoCaoNamActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    ColorStateList def;
    TextView txtthang, txtnam, select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_bao_cao_nam);
        txtthang = findViewById(R.id.txtthang);
        txtnam = findViewById(R.id.txtnam);
        select = findViewById(R.id.select);

        // Store the default text color
        def = txtthang.getTextColors();

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);
        txtthang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.animate().x(0).setDuration(100);
                txtthang.setTextColor(Color.WHITE);
                txtnam.setTextColor(def);

                Intent intent = new Intent(TrangBaoCaoNamActivity.this, TrangBaoCaoActivity.class);
                startActivity(intent);
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
    }
}