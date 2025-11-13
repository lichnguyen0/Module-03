package org.example.quanlyphongtro.model;

import java.time.LocalDate;

public class PhongTro {
    private int maPhongTro;
    private String tenNguoiThue;
    private String soDienThoai;
    private LocalDate ngayBatDau;
    private int maHinhThuc;
    private String tenHinhThuc;
    private String ghiChu;

    public PhongTro() {
    }

    public PhongTro(int maPhongTro, String tenNguoiThue, String soDienThoai,
                    LocalDate ngayBatDau, int maHinhThuc, String tenHinhThuc, String ghiChu) {
        this.maPhongTro = maPhongTro;
        this.tenNguoiThue = tenNguoiThue;
        this.soDienThoai = soDienThoai;
        this.ngayBatDau = ngayBatDau;
        this.maHinhThuc = maHinhThuc;
        this.tenHinhThuc = tenHinhThuc;
        this.ghiChu = ghiChu;
    }

    public int getMaPhongTro() {
        return maPhongTro;
    }

    public void setMaPhongTro(int maPhongTro) {
        this.maPhongTro = maPhongTro;
    }

    public String getMaPhongTroFormatted() {
        return String.format("PT-%03d", maPhongTro);
    }

    public String getTenNguoiThue() {
        return tenNguoiThue;
    }

    public void setTenNguoiThue(String tenNguoiThue) {
        this.tenNguoiThue = tenNguoiThue;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public java.sql.Date getNgayBatDauDate() {
        return java.sql.Date.valueOf(this.ngayBatDau);
    }

    public int getMaHinhThuc() {
        return maHinhThuc;
    }

    public void setMaHinhThuc(int maHinhThuc) {
        this.maHinhThuc = maHinhThuc;
    }

    public String getTenHinhThuc() {
        return tenHinhThuc;
    }

    public void setTenHinhThuc(String tenHinhThuc) {
        this.tenHinhThuc = tenHinhThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
