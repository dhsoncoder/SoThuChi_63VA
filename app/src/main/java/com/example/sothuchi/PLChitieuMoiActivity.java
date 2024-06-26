package com.example.sothuchi;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class PLChitieuMoiActivity extends AppCompatActivity {

    private int[] imageName = {R.drawable.am_nhac, R.drawable.an_uong, R.drawable.atm, R.drawable.boi, R.drawable.ca_phe, R.drawable.cake, R.drawable.cham_con, R.drawable.choi_game, R.drawable.chup_anh, R.drawable.cuoc_goi, R.drawable.dien, R.drawable.dien_thoai, R.drawable.do_an, R.drawable.do_xang, R.drawable.hoc_phi, R.drawable.hoc_phi2, R.drawable.kem, R.drawable.laptop, R.drawable.mang_thai, R.drawable.may_bay, R.drawable.mua_sam, R.drawable.ngan_hang, R.drawable.ngu, R.drawable.pin, R.drawable.ruou, R.drawable.tai_nghe, R.drawable.the_thao, R.drawable.thu_cung, R.drawable.thuoc_la, R.drawable.tien_mang, R.drawable.tien_nha, R.drawable.tin_nhan, R.drawable.trong_cay, R.drawable.y_te, R.drawable.yeu_duong, R.drawable.xa_giao, R.drawable.sac_dien, R.drawable.tiec_tung };
    private GridView gridViewDemo;
    private MyarrayAdapter adapterDanhSach;
    private ArrayList<Image> arrimage;

    private ImageView imgchitieumoi;
    private CardView cardviewcolor;
    private Button btndoimau;
    private AlertDialog dialog; // Declare the dialog here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plchitieu_moi);

        // Initialize views
        gridViewDemo = findViewById(R.id.gridviewchitieu);
        imgchitieumoi = findViewById(R.id.imgchitieumoi);
        cardviewcolor = findViewById(R.id.cardviewcolor);
        btndoimau = findViewById(R.id.btndoimau);

        arrimage = new ArrayList<>();
        adapterDanhSach = new MyarrayAdapter(PLChitieuMoiActivity.this, R.layout.listicon, arrimage);
        gridViewDemo.setAdapter(adapterDanhSach);

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
                imgchitieumoi.setImageResource(selectedImage.getImg());
                // Reset cardviewcolor to default color (GRAY)
                cardviewcolor.setCardBackgroundColor(Color.GRAY);
            }
        });

        // Set click listener for btndoimau to change cardviewcolor
        btndoimau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerDialog();
            }
        });
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
                    int selectedColor = Color.parseColor((String) v.getTag());
                    // Set the color to both btndoimau and cardviewcolor
                    btndoimau.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
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
}
