package com.example.sothuchi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TrangDSPhanloaiActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView lvChiTieu;
    private ListView lvThuNhap;
    private PLChiTieuAdapter adapterChiTieu;
    private PLThuNhapAdapter adapterThuNhap;
    private List<DanhMuc> danhMucChiTieuList;
    private List<DanhMuc> danhMucThuNhapList;
    private ImageButton btnquaylai, btnthemPL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_dsphanloai);

        dbHelper = new DatabaseHelper(this);
        lvChiTieu = findViewById(R.id.lv1);
        lvThuNhap = findViewById(R.id.lv2);

        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        // Tab Chi tiêu
        TabHost.TabSpec tab1 = tabHost.newTabSpec("ChiTieu");
        tab1.setIndicator("Chi tiêu");
        tab1.setContent(R.id.tabChitieu);
        tabHost.addTab(tab1);

        // Tab Thu nhập
        TabHost.TabSpec tab2 = tabHost.newTabSpec("ThuNhap");
        tab2.setIndicator("Thu nhập");
        tab2.setContent(R.id.tabThunhap);
        tabHost.addTab(tab2);

        setTabColor(tabHost);

        btnquaylai = findViewById(R.id.btnquaylai);
        btnthemPL = findViewById(R.id.btnthemPL);

        // Xử lý sự kiện click nút quay lại
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnthemPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(TrangDSPhanloaiActivity.this);

                // Thiết lập tiêu đề và thông điệp
                builder.setTitle("Thêm mới");
                builder.setMessage("Bạn muốn thêm mới chi tiêu hay thu nhập?");

                // Thiết lập các nút và xử lý sự kiện khi người dùng chọn
                builder.setPositiveButton("Chi tiêu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Chuyển hướng sang activity tạo mới đơn vị
                        Intent intent = new Intent(TrangDSPhanloaiActivity.this, PLChitieuMoiActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Thu nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Chuyển hướng sang activity tạo mới nhân viên
                        Intent intent = new Intent(TrangDSPhanloaiActivity.this, PLThunhapMoiActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng hộp thoại
                    }
                });

                // Tạo và hiển thị dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Load danh sách chi tiêu từ database và hiển thị vào ListView
        loadDanhMucChiTieu();
        // Load danh sách thu nhập từ database và hiển thị vào ListView
        loadDanhMucThuNhap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load lại dữ liệu khi activity quay lại từ PLChitieuMoiActivity hoặc PLThunhapMoiActivity
        loadDanhMucChiTieu();
        loadDanhMucThuNhap();
    }

    private void loadDanhMucChiTieu() {
        danhMucChiTieuList = dbHelper.getAllDanhMucByLoai(0); // Loai 0 là chi tiêu
        adapterChiTieu = new PLChiTieuAdapter(this, danhMucChiTieuList);
        lvChiTieu.setAdapter(adapterChiTieu);
        adapterChiTieu.notifyDataSetChanged();
    }

    private void loadDanhMucThuNhap() {
        danhMucThuNhapList = dbHelper.getAllDanhMucByLoai(1); // Loai 1 là thu nhập
        adapterThuNhap = new PLThuNhapAdapter(this, danhMucThuNhapList);
        lvThuNhap.setAdapter(adapterThuNhap);
        adapterThuNhap.notifyDataSetChanged();
    }

    private void setTabColor(TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).getBackground().setColorFilter(getResources().getColor(R.color.tab_background), PorterDuff.Mode.MULTIPLY); // Change tab background color if needed
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); // Get the title view
            tv.setTextColor(getResources().getColor(R.color.white)); // Change the color
        }
    }
}
