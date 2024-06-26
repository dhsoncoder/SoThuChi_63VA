package com.example.sothuchi;

public class GroupedThuchiItem {
    private String tenDanhmuc;
    private String mauSac;
    private int bieutuong;
    private double soTien;
    private int count; // Add this field

    public GroupedThuchiItem(String tenDanhmuc, String mauSac, int bieutuong, double soTien) {
        this.tenDanhmuc = tenDanhmuc;
        this.mauSac = mauSac;
        this.bieutuong = bieutuong;
        this.soTien = soTien;
        this.count = 1; // Initialize count to 1
    }

    public String getTenDanhmuc() {
        return tenDanhmuc;
    }

    public void setTenDanhmuc(String tenDanhmuc) {
        this.tenDanhmuc = tenDanhmuc;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public int getBieutuong() {
        return bieutuong;
    }

    public void setBieutuong(int bieutuong) {
        this.bieutuong = bieutuong;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public void addSoTien(double soTien) {
        this.soTien += soTien;
        this.count++; // Increment count
    }

    public int getCount() {
        return count;
    }
}
