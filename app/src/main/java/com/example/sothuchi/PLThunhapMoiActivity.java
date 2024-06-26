package com.example.sothuchi;

import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class PLThunhapMoiActivity extends AppCompatActivity {

    private int[] imageName = {R.drawable.am_nhac, R.drawable.an_uong, R.drawable.atm, R.drawable.boi, R.drawable.ca_phe, R.drawable.cake, R.drawable.cham_con, R.drawable.choi_game, R.drawable.chup_anh, R.drawable.cuoc_goi, R.drawable.dien, R.drawable.dien_thoai, R.drawable.do_an, R.drawable.do_xang, R.drawable.hoc_phi, R.drawable.hoc_phi2, R.drawable.kem, R.drawable.laptop, R.drawable.mang_thai, R.drawable.may_bay, R.drawable.mua_sam, R.drawable.ngan_hang, R.drawable.ngu, R.drawable.pin, R.drawable.ruou, R.drawable.tai_nghe, R.drawable.the_thao, R.drawable.thu_cung, R.drawable.thuoc_la, R.drawable.tien_mang, R.drawable.tien_nha, R.drawable.tin_nhan, R.drawable.trong_cay, R.drawable.y_te, R.drawable.yeu_duong, R.drawable.xa_giao, R.drawable.sac_dien, R.drawable.tiec_tung };
    private GridView gridViewDemo;
    private MyarrayAdapter adapterDanhSach;
    private ArrayList<Image> arrimage;

    private ImageView imgThuNhapMoi;
    private CardView cardviewcolor;
    private EditText edtThuNhapPLMoi;
    private Button btnDoiMau;
    private AlertDialog dialog;
    private ImageButton btnXong, btnQuayLai;

    private DatabaseHelper dbHelper;

    private int selectedColor = Color.GRAY;
    private int selectedImageRes = R.drawable.an_uong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plthunhap_moi);

        // Initialize views
        gridViewDemo = findViewById(R.id.gridviewthunhap);
        imgThuNhapMoi = findViewById(R.id.imgthunhapmoi);
        cardviewcolor = findViewById(R.id.cardviewcolor);
        btnDoiMau = findViewById(R.id.btndoimau);

        arrimage = new ArrayList<>();
        adapterDanhSach = new MyarrayAdapter(PLThunhapMoiActivity.this, R.layout.listicon, arrimage);
        gridViewDemo.setAdapter(adapterDanhSach);
        btnXong = findViewById(R.id.btnxong);
        btnQuayLai = findViewById(R.id.btnquaylai);

        // Populate GridView with images
        for (int i = 0; i < imageName.length; i++) {
            Image myimage = new Image();
            myimage.setImg(imageName[i]);
            arrimage.add(myimage);
        }

        // Set click listener for GridView items
        gridViewDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image selectedImage = arrimage.get(position);
                selectedImageRes = selectedImage.getImg();
                imgThuNhapMoi.setImageResource(selectedImageRes);
                // Reset cardviewcolor to default color (GRAY)
                cardviewcolor.setCardBackgroundColor(Color.GRAY);
            }
        });

        // Set click listener for btnDoiMau to change cardviewcolor
        btnDoiMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerDialog();
            }
        });

        // Xử lý sự kiện click nút quay lại
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themPLThuNhap();
            }
        });

        // Initialize the database helper
        dbHelper = new DatabaseHelper(this);
    }

    private void openColorPickerDialog() {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Color");

        // Inflate the color picker layout
        View colorPickerView = getLayoutInflater().inflate(R.layout.color_picker_dialog, null);
        builder.setView(colorPickerView);

        // Get the GridLayout and set up color CardView click listeners
        GridLayout colorGrid = colorPickerView.findViewById(R.id.colorGrid);
        for (int i = 0; i < colorGrid.getChildCount(); i++) {
            View colorCard = colorGrid.getChildAt(i);
            colorCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the color from the CardView tag
                    selectedColor = Color.parseColor((String) v.getTag());
                    // Set the color to both btnDoiMau and cardviewcolor
                    btnDoiMau.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
                    cardviewcolor.setCardBackgroundColor(selectedColor);
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });
        }

        // Show the dialog
        dialog = builder.create();
        dialog.show();
    }

    private void themPLThuNhap() {
        // Lấy tên danh mục nhập vào
        edtThuNhapPLMoi = findViewById(R.id.edtthunhapPLmoi);
        String tenDanhMuc = edtThuNhapPLMoi.getText().toString().trim();

        // Kiểm tra nếu tên danh mục không trống
        if (tenDanhMuc.isEmpty()) {
            edtThuNhapPLMoi.setError("Vui lòng nhập tên danh mục");
            edtThuNhapPLMoi.requestFocus();
            return;
        }

        // Sử dụng biểu tượng mặc định nếu chưa chọn
        if (selectedImageRes == 0) {
            selectedImageRes = R.drawable.an_uong;
        }

        // Sử dụng màu mặc định nếu chưa chọn
        if (selectedColor == Color.GRAY) {
            selectedColor = Color.GRAY;
        }

        // Chuẩn bị các giá trị để chèn vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEN_DANHMUC, tenDanhMuc);
        values.put(DatabaseHelper.COLUMN_LOAI, 1); // loai = 0
        values.put(DatabaseHelper.COLUMN_BIEUTUONG, selectedImageRes);
        values.put(DatabaseHelper.COLUMN_MAUSAC, String.format("#%06X", (0xFFFFFF & selectedColor)));

        // Chèn danh mục mới vào cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long ketQua = db.insert(DatabaseHelper.TABLE_DANHMUC, null, values);

        // Kiểm tra nếu việc chèn thành công
        if (ketQua == -1) {
            // Chèn thất bại, hiển thị thông báo lỗi
            Toast.makeText(this, "Thêm danh mục thất bại", Toast.LENGTH_SHORT).show();
        } else {
            // Chèn thành công, hiển thị thông báo thành công và kết thúc Activity
            Toast.makeText(this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
