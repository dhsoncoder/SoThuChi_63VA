package com.example.sothuchi;

public class Constants {
    // database or db name
    public static final String DATABASE_NAME = "sothuchi";

    // database version
    public static final int DATABASE_VERSION = 1;

    // table name
    public static final String TABLE_DANHMUC = "danhmuc";
    public static final String TABLE_THUCHI = "thuchi";

    // table column or field name DANHMUC
    public static final String madm = "madanhmuc";
    public static final String tendm = "tendanhmuc";
    public static final String loai = "loai";

    // table column or field name THUCHI
    public static final String matc = "mathuchi";
    public static final String sotien = "sotien";
    public static final String ngaytc = "ngaythuchi";
    public static final String ghichu = "ghichu";

    // query for create table DANHMUC
    public static final String CREATE_TABLE_DANHMUC = "CREATE TABLE " + TABLE_DANHMUC + "( "
            + madm + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + tendm + " TEXT, "
            + loai + " INTEGER"
            + " );";

    public static final String CREATE_TABLE_THUCHI = "CREATE TABLE " + TABLE_THUCHI + "( "
            + matc + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + sotien + " REAL, "
            + loai + " INTEGER, "
            + ngaytc + " TEXT, "
            + ghichu + " TEXT, "
            + madm + " INTEGER, "
            + " FOREIGN KEY (" + madm + ") REFERENCES " + TABLE_DANHMUC + "(" + madm + ")"
            + ");";

}
