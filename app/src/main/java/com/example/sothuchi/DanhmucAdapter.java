package com.example.sothuchi;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DanhmucAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private int selectedPosition = -1;

    public DanhmucAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        cursor.moveToPosition(position);
        return cursor;
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_DANHMUC));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        cursor.moveToPosition(position);

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.name);

        int iconResId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIEUTUONG));
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEN_DANHMUC));

        icon.setImageResource(iconResId);
        name.setText(nameText);

        // Apply a border if the item is selected
        if (position == selectedPosition) {
            convertView.setBackgroundResource(R.drawable.selected_item_border);
        } else {
            convertView.setBackgroundResource(android.R.color.transparent);
        }

        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    // New method to swap cursor and refresh data
    public void swapCursor(Cursor newCursor) {
        if (newCursor != cursor) {
            cursor = newCursor;
            notifyDataSetChanged();
        }
    }
}
