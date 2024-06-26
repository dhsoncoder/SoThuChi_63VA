package com.example.sothuchi;

public class ThuChi {
    private int id;
    private double soTien;
    private int loai; // 1 for thu, 0 for chi
    private String ngayThang,tendanhmuc;
    private String ghiChu;
    private int idDanhMuc;

    public ThuChi(int id, double soTien, int loai, String ngayThang, String ghiChu,int idDanhMuc) {
        this.id = id;
        this.soTien = soTien;
        this.loai = loai;
        this.ngayThang = ngayThang;
        this.ghiChu = ghiChu;
        this.idDanhMuc = idDanhMuc;
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

    @Override
    public String toString() {
        return "ThuChi{" +
                "id=" + id +
                ", soTien=" + soTien +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }

    public String getGhiChu() {
        return ghiChu;
    }
    public int getIdDanhMuc() {
        return idDanhMuc;}
}
