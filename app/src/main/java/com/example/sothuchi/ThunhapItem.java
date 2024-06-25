package com.example.sothuchi;

public class ThunhapItem {
    private String tenDanhmuc;
    private double soTien;
    private int bieuTuong;
    private String mausac;
    private float phanTram; // Field for percentage
    private int soLuong; // Field for count

    public ThunhapItem(String tenDanhmuc, float phanTram, int soLuong, double soTien, int bieuTuong, String mausac) {
        this.tenDanhmuc = tenDanhmuc;
        this.phanTram = phanTram;
        this.soLuong = soLuong;
        this.soTien = soTien;
        this.bieuTuong = bieuTuong;
        this.mausac = mausac;
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
        return mausac;
    }

    public void setMauSac(String mausac) {
        this.mausac = mausac;
    }

    public float getPhanTram() {
        return phanTram;
    }

    public void setPhanTram(float phanTram) {
        this.phanTram = phanTram;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
