package com.example.sothuchi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

public class CustomArrayAdapter extends BaseSwipeAdapter {
    private Activity context;
    private int idLayout;
    private ArrayList<ThuChi> mylist;
    private DatabaseHelper databaseHelper;
    private ThuChiViewModel viewModel;
    public CustomArrayAdapter(Activity context, int idLayout, ArrayList<ThuChi> mylist, ThuChiViewModel viewModel) {
        this.context = context;
        this.idLayout = idLayout;
        this.mylist = mylist;
        this.databaseHelper = new DatabaseHelper(context);
        this.viewModel = viewModel;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View convertView = LayoutInflater.from(context).inflate(idLayout, parent, false);
        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        SwipeLayout swipeLayout = convertView.findViewById(R.id.swipe_layout);

        TextView ngaythang = convertView.findViewById(R.id.tv_ngaythang);
        TextView sotien = convertView.findViewById(R.id.tv_sotien);
        TextView noidung = convertView.findViewById(R.id.tv_tenmuc);
        Button deleteButton = convertView.findViewById(R.id.delete);

        ThuChi thuChi = mylist.get(position);

        ngaythang.setText(thuChi.getNgayThang());
        sotien.setText(String.format("%,.0fđ",thuChi.getSoTien()));
        noidung.setText(thuChi.getGhiChu());

        // Handle click events or swipe actions here
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the item from the database
                databaseHelper.deleteThuchi(thuChi.getId());

                // Close the swipe layout
                swipeLayout.close();

                // Remove the item from the list and update the adapter
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();
                viewModel.setThuChiList(mylist);

            }
        });

        // Example: Close the swipe layout on click of delete button

    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
