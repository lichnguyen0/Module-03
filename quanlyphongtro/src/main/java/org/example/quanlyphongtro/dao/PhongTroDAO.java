package org.example.quanlyphongtro.dao;

import org.example.quanlyphongtro.model.HinhThucThanhToan;
import org.example.quanlyphongtro.model.PhongTro;
import org.example.quanlyphongtro.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongTroDAO {

    // ======================== MAPPER ========================
    private PhongTro mapPhongTro(ResultSet rs) throws SQLException {
        PhongTro pt = new PhongTro();

        pt.setMaPhongTro(rs.getInt("ma_phong_tro"));
        pt.setTenNguoiThue(rs.getString("ten_nguoi_thue"));
        pt.setSoDienThoai(rs.getString("so_dien_thoai"));

        Date sqlDate = rs.getDate("ngay_bat_dau");
        if (sqlDate != null) {
            pt.setNgayBatDau(sqlDate.toLocalDate());
        }

        pt.setMaHinhThuc(rs.getInt("ma_hinh_thuc"));
        pt.setTenHinhThuc(rs.getString("ten_hinh_thuc"));
        pt.setGhiChu(rs.getString("ghi_chu"));

        return pt;
    }

    // ======================== GET ALL ========================
    public List<PhongTro> getAllPhongTro() {
        List<PhongTro> list = new ArrayList<>();
        String sql =
                "SELECT pt.*, ht.ten_hinh_thuc FROM phong_tro pt " +
                        "JOIN hinh_thuc_thanh_toan ht ON pt.ma_hinh_thuc = ht.ma_hinh_thuc " +
                        "ORDER BY pt.ma_phong_tro DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapPhongTro(rs));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi getAllPhongTro: " + e.getMessage());
        }

        return list;
    }

    // ======================== SEARCH ========================
    public List<PhongTro> searchPhongTro(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPhongTro();
        }

        List<PhongTro> list = new ArrayList<>();

        String sql =
                "SELECT pt.*, ht.ten_hinh_thuc FROM phong_tro pt " +
                        "JOIN hinh_thuc_thanh_toan ht ON pt.ma_hinh_thuc = ht.ma_hinh_thuc " +
                        "WHERE pt.ten_nguoi_thue LIKE ? " +
                        "OR pt.so_dien_thoai LIKE ? " +
                        "OR pt.ma_phong_tro = ? " +
                        "ORDER BY pt.ma_phong_tro DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);

            // Nếu keyword là số → tìm theo mã phòng
            try {
                ps.setInt(3, Integer.parseInt(keyword));
            } catch (NumberFormatException e) {
                ps.setInt(3, -1); // giá trị không tồn tại để tránh lỗi
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapPhongTro(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi searchPhongTro: " + e.getMessage());
        }

        return list;
    }

    // ======================== ADD ========================
    public boolean addPhongTro(PhongTro phongTro) {
        String sql =
                "INSERT INTO phong_tro (ten_nguoi_thue, so_dien_thoai, ngay_bat_dau, ma_hinh_thuc, ghi_chu) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phongTro.getTenNguoiThue());
            ps.setString(2, phongTro.getSoDienThoai());
            ps.setDate(3, Date.valueOf(phongTro.getNgayBatDau()));
            ps.setInt(4, phongTro.getMaHinhThuc());
            ps.setString(5, phongTro.getGhiChu());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi addPhongTro: " + e.getMessage());
            return false;
        }
    }

    // ======================== DELETE ========================
    public boolean deletePhongTro(int[] ids) {
        if (ids == null || ids.length == 0) return false;

        StringBuilder sql = new StringBuilder("DELETE FROM phong_tro WHERE ma_phong_tro IN (");
        for (int i = 0; i < ids.length; i++) {
            sql.append("?");
            if (i < ids.length - 1) sql.append(",");
        }
        sql.append(")");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < ids.length; i++) {
                ps.setInt(i + 1, ids[i]);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi deletePhongTro: " + e.getMessage());
            return false;
        }
    }

    // ======================== GET ALL PAYMENT TYPE ========================
    public List<HinhThucThanhToan> getAllHinhThucThanhToan() {
        List<HinhThucThanhToan> list = new ArrayList<>();
        String sql = "SELECT * FROM hinh_thuc_thanh_toan";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HinhThucThanhToan ht = new HinhThucThanhToan();
                ht.setMaHinhThuc(rs.getInt("ma_hinh_thuc"));
                ht.setTenHinhThuc(rs.getString("ten_hinh_thuc"));
                list.add(ht);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi getAllHinhThucThanhToan: " + e.getMessage());
        }

        return list;
    }
}
