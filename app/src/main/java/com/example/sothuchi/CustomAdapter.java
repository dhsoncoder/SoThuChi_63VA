package com.example.sothuchi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ThuchiItem> {

    private Context mContext;
    private int mResource;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ThuchiItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txttenPL = convertView.findViewById(R.id.txttenPL);
            holder.txtphantramPL = convertView.findViewById(R.id.txtphantramPL);
            holder.txtsoluongPL = convertView.findViewById(R.id.txtsoluongPL);
            holder.txttienPL = convertView.findViewById(R.id.txttienPL);
            holder.imgBieuTuong = convertView.findViewById(R.id.imageView2); // ImageView for icon
            holder.progressBar = convertView.findViewById(R.id.progress1); // ProgressBar
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ThuchiItem item = getItem(position);

        if (item != null) {
            holder.txttenPL.setText(item.getTenDanhmuc());
            holder.txtphantramPL.setText("50%"); // Example, replace with actual percentage logic
            holder.txtsoluongPL.setText("1"); // Example, replace with actual quantity logic
            holder.txttienPL.setText(String.valueOf(item.getSoTien()));

            // Set icon and color
            holder.imgBieuTuong.setImageResource(item.getBieuTuong()); // Assuming bieuTuong is a drawable resource ID

            try {
                int color = android.graphics.Color.parseColor(item.getMauSac()); // Set color from item's mauSac
                ((CardView) convertView.findViewById(R.id.cardviewcolor)).setCardBackgroundColor(color);
            } catch (IllegalArgumentException exception) {
                exception.printStackTrace();
            }

            holder.progressBar.setProgress(50); // Example, replace with actual progress logic
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView txttenPL;
        TextView txtphantramPL;
        TextView txtsoluongPL;
        TextView txttienPL;
        ImageView imgBieuTuong;
        ProgressBar progressBar;
    }
}
