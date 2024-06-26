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
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_danhmuc, parent, false);
        }

        cursor.moveToPosition(position);

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.name);

        int iconResId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIEUTUONG));
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEN_DANHMUC));

        icon.setImageResource(iconResId);
        name.setText(nameText);

        return convertView;
    }
}
