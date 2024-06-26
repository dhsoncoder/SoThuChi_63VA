package com.example.sothuchi;

public class DanhMuc {
    private long id; // Added id field
    private int maDanhMuc;
    private String tenDanhMuc;
    private int bieuTuong;
    private String mauSac;

    public DanhMuc() {
        // Default constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getBieuTuong() {
        return bieuTuong;
    }

    public void setBieuTuong(int bieuTuong) {
        this.bieuTuong = bieuTuong;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }
}
