package com.example.sothuchi;

public class ThuChi {
    private int id;
    private double soTien;
    private int loai; // 1 for thu, 0 for chi
    private String ngayThang;
    private String ghiChu;

    public ThuChi(int id, double soTien, int loai, String ngayThang, String ghiChu) {
        this.id = id;
        this.soTien = soTien;
        this.loai = loai;
        this.ngayThang = ngayThang;
        this.ghiChu = ghiChu;
    }

    public int getId() {
        return id;
    }

    public double getSoTien() {
        return soTien;
    }

    public int getLoai() {
        return loai;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public String getGhiChu() {
        return ghiChu;
    }
}
