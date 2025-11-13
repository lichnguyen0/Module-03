package org.example.quanlyphongtro.controller;

import org.example.quanlyphongtro.dao.PhongTroDAO;
import org.example.quanlyphongtro.model.HinhThucThanhToan;
import org.example.quanlyphongtro.model.PhongTro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/phong-tro")
public class PhongTroServlet extends HttpServlet {

    private PhongTroDAO dao = new PhongTroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        List<PhongTro> listPhongTro;

        if ("search".equals(action)) {
            String keyword = request.getParameter("keyword");
            if (keyword == null) keyword = "";
            listPhongTro = dao.searchPhongTro(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            listPhongTro = dao.getAllPhongTro();
        }

        request.setAttribute("listPhongTro", listPhongTro);

        List<HinhThucThanhToan> listHinhThuc = dao.getAllHinhThucThanhToan();
        request.setAttribute("listHinhThuc", listHinhThuc);

        request.getRequestDispatcher("/view/phong-tro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equals(action)) {

            String tenNguoiThue = request.getParameter("tenNguoiThue").trim();
            String soDienThoai = request.getParameter("soDienThoai").trim();
            String ngayBatDauStr = request.getParameter("ngayBatDau").trim();
            String maHinhThucStr = request.getParameter("maHinhThuc");
            String ghiChu = request.getParameter("ghiChu");

            List<HinhThucThanhToan> listHinhThuc = dao.getAllHinhThucThanhToan();
            request.setAttribute("listHinhThuc", listHinhThuc);

            // VALIDATION
            String error = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate ngayBatDau = null;

            // Cho phép tiếng Việt
            if (!tenNguoiThue.matches("[\\p{L} ]{5,50}")) {
                error = "Tên người thuê không hợp lệ (5-50 ký tự, chỉ chữ cái).";
            } else if (!soDienThoai.matches("\\d{10}")) {
                error = "Số điện thoại phải gồm 10 số.";
            } else {
                try {
                    ngayBatDau = LocalDate.parse(ngayBatDauStr, formatter);
                    if (ngayBatDau.isBefore(LocalDate.now())) {
                        error = "Ngày bắt đầu không được nhỏ hơn ngày hiện tại.";
                    }
                } catch (DateTimeParseException e) {
                    error = "Ngày bắt đầu không đúng định dạng dd-MM-yyyy.";
                }
            }

            int maHinhThuc = 0;
            try {
                maHinhThuc = Integer.parseInt(maHinhThucStr);
            } catch (Exception e) {
                error = "Hình thức thanh toán không hợp lệ.";
            }

            // Nếu lỗi → mở lại modal
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("showModal", true);

                request.setAttribute("tenNguoiThue", tenNguoiThue);
                request.setAttribute("soDienThoai", soDienThoai);
                request.setAttribute("ngayBatDau", ngayBatDauStr);
                request.setAttribute("maHinhThuc", maHinhThuc);
                request.setAttribute("ghiChu", ghiChu);

                request.setAttribute("listPhongTro", dao.getAllPhongTro());

                request.getRequestDispatcher("/view/phong-tro.jsp").forward(request, response);
                return;
            }

            // INSERT
            PhongTro pt = new PhongTro();
            pt.setTenNguoiThue(tenNguoiThue);
            pt.setSoDienThoai(soDienThoai);
            pt.setNgayBatDau(ngayBatDau);
            pt.setMaHinhThuc(maHinhThuc);
            pt.setGhiChu(ghiChu);

            dao.addPhongTro(pt);

            response.sendRedirect(request.getContextPath() + "/phong-tro?success=true");
        }

        // DELETE
        else if ("delete".equals(action)) {

            String[] selectedIds = request.getParameterValues("selectedIds");

            if (selectedIds == null) {
                response.sendRedirect(request.getContextPath()
                        + "/phong-tro?error=Vui lòng chọn phòng trọ để xóa");
                return;
            }

            int[] ids = new int[selectedIds.length];
            for (int i = 0; i < selectedIds.length; i++) {
                ids[i] = Integer.parseInt(selectedIds[i]);
            }

            dao.deletePhongTro(ids);

            response.sendRedirect(request.getContextPath() + "/phong-tro?deleteSuccess=true");
        }
    }
}
