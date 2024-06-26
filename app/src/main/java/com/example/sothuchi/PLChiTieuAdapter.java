package com.example.sothuchi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import java.util.List;

public class PLChiTieuAdapter extends ArrayAdapter<DanhMuc> {

    private Context context;
    private List<DanhMuc> danhMucList;
    private DatabaseHelper dbHelper;

    public PLChiTieuAdapter(@NonNull Context context, @NonNull List<DanhMuc> objects) {
        super(context, 0, objects);
        this.context = context;
        this.danhMucList = objects;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
        }

        final DanhMuc danhMuc = danhMucList.get(position);

        ImageView imageView = view.findViewById(R.id.imageView2);
        TextView textView = view.findViewById(R.id.textView);
        ImageButton btnSua = view.findViewById(R.id.btnsua);
        ImageButton btnXoa = view.findViewById(R.id.btnxoa);
        CardView cardView = view.findViewById(R.id.cardviewcolor); // Assumes there's a CardView with this ID in listitem layout

        // Set image resource and text
        imageView.setImageResource(danhMuc.getBieuTuong());
        textView.setText(danhMuc.getTenDanhMuc());

        // Set color for icon to white
        imageView.setColorFilter(Color.WHITE);

        // Set background color for CardView
        if (danhMuc.getMauSac() != null && !danhMuc.getMauSac().isEmpty()) {
            try {
                int color = Color.parseColor(danhMuc.getMauSac());
                cardView.setCardBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        // Xử lý sự kiện nút xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận trước khi xóa
                showConfirmDeleteDialog(danhMuc);
            }
        });

        // Xử lý sự kiện nút sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang chỉnh sửa danh mục
                Intent intent = new Intent(context, SuaPLChitieuActivity.class);
                intent.putExtra("maDanhMuc", danhMuc.getMaDanhMuc());
                intent.putExtra("tenDanhMuc", danhMuc.getTenDanhMuc());
                intent.putExtra("bieuTuong", danhMuc.getBieuTuong());
                intent.putExtra("mauSac", danhMuc.getMauSac());
                context.startActivity(intent);
            }
        });


        return view;
    }

    private void showConfirmDeleteDialog(final DanhMuc danhMuc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa mục này?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện xóa dữ liệu từ SQLite
                dbHelper.deleteDanhMuc(danhMuc.getId());

                // Xóa khỏi danh sách và cập nhật giao diện
                danhMucList.remove(danhMuc);
                notifyDataSetChanged();

                Toast.makeText(context, "Đã xóa danh mục thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
