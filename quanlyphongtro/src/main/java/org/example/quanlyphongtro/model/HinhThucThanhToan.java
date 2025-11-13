package org.example.quanlyphongtro.model;

public class HinhThucThanhToan {
    private int maHinhThuc;
    private String tenHinhThuc;

    public HinhThucThanhToan() {
    }

    public HinhThucThanhToan(int maHinhThuc, String tenHinhThuc) {
        this.maHinhThuc = maHinhThuc;
        this.tenHinhThuc = tenHinhThuc;
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
}