package com.example.sothuchi;

public class ThuchiItem {
    private String tenDanhmuc;
    private double soTien;
    private int bieuTuong; // Bieu tuong as integer resource ID
    private String mauSac; // Mau sac as hexadecimal string

    public ThuchiItem() {
        // Default constructor required for Firebase
    }

    public ThuchiItem(String tenDanhmuc, double soTien, int bieuTuong, String mauSac) {
        this.tenDanhmuc = tenDanhmuc;
        this.soTien = soTien;
        this.bieuTuong = bieuTuong;
        this.mauSac = mauSac;
    }

    public String getTenDanhmuc() {
        return tenDanhmuc;
    }

    public void setTenDanhmuc(String tenDanhmuc) {
        this.tenDanhmuc = tenDanhmuc;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
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
