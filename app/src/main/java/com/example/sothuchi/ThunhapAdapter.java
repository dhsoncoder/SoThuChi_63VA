package com.example.sothuchi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sothuchi.ThunhapAdapter;
import com.example.sothuchi.ThunhapItem;

import java.util.ArrayList;


public class ThunhapAdapter extends RecyclerView.Adapter<ThunhapAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ThunhapItem> mThunhapItems;

    public ThunhapAdapter(Context context, ArrayList<ThunhapItem> ThunhapItems) {
        mContext = context;
        mThunhapItems = ThunhapItems;
    }

    @NonNull
    @Override
    public ThunhapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_baocao, parent, false);
        return new ThunhapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThunhapAdapter.ViewHolder holder, int position) {
        ThunhapItem item = mThunhapItems.get(position);

        holder.txttenPL.setText(item.getTenDanhmuc());
        holder.txtphantramPL.setText(String.format("%.2f%%", item.getPhanTram()));
        holder.txtsoluongPL.setText(String.valueOf(item.getSoLuong()));
        holder.txttienPL.setText(String.valueOf(item.getSoTien()));
        holder.imgBieuTuong.setImageResource(item.getBieuTuong());

        try {
            int color = Color.parseColor(item.getMauSac());
            ((CardView) holder.itemView.findViewById(R.id.cardviewcolor)).setCardBackgroundColor(color);
        } catch (IllegalArgumentException ignored) {
            // Handle appropriately
        }

        holder.progressBar.setProgress((int) item.getPhanTram());
    }

    @Override
    public int getItemCount() {
        return mThunhapItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttenPL;
        TextView txtphantramPL;
        TextView txtsoluongPL;
        TextView txttienPL;
        ImageView imgBieuTuong;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenPL = itemView.findViewById(R.id.txttenPL);
            txtphantramPL = itemView.findViewById(R.id.txtphantramPL);
            txtsoluongPL = itemView.findViewById(R.id.txtsoluongPL);
            txttienPL = itemView.findViewById(R.id.txttienPL);
            imgBieuTuong = itemView.findViewById(R.id.imageView2);
            progressBar = itemView.findViewById(R.id.progress1);
        }
    }
}