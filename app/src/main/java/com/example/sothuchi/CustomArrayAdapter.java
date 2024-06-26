package com.example.sothuchi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<ThuChi> {
    Activity context;
    int idLayout;

    public CustomArrayAdapter( Activity context, int idLayout, ArrayList<ThuChi> mylist) {
        super(context, idLayout,mylist);
        this.context = context;
        this.idLayout = idLayout;
        this.mylist = mylist;
    }
    ArrayList<ThuChi> mylist;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myflacter = context.getLayoutInflater();
        convertView = myflacter.inflate(idLayout,null);
        ThuChi thuChi = mylist.get(position);
        TextView ngaythang = convertView.findViewById(R.id.tv_ngaythang);
        ngaythang.setText(thuChi.getNgayThang());
        TextView sotien = convertView.findViewById(R.id.tv_sotien);
        sotien.setText(thuChi.getSoTien()+"");
        TextView noidung = convertView.findViewById(R.id.tv_tenmuc);
        noidung.setText(thuChi.getGhiChu());
        return convertView;
    }
}
