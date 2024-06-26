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

import java.util.ArrayList;

public class ChitieuAdapter extends RecyclerView.Adapter<ChitieuAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ThuchiItem> mThuchiItems;

    public ChitieuAdapter(Context context, ArrayList<ThuchiItem> thuchiItems) {
        mContext = context;
        mThuchiItems = thuchiItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_baocao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThuchiItem item = mThuchiItems.get(position);

        holder.txttenPL.setText(item.getTenDanhmuc());
        holder.txtphantramPL.setText(String.format("%.0f%%", item.getPhanTram()));
        holder.txtsoluongPL.setText(String.valueOf(item.getSoLuong()));
        holder.txttienPL.setText(String.format("-%,.0f",item.getSoTien()));
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
        return mThuchiItems.size();
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
