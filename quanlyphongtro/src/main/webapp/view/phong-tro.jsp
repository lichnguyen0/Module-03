<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>Danh sách phòng trọ</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">

  <style>
    body { background-color:#f8f9fa; font-family:'Segoe UI', Tahoma, sans-serif; }
    .container-fluid { padding:30px; }
    .card { border:none; border-radius:15px; box-shadow:0 2px 10px rgba(0,0,0,0.1); }
    .card-header { background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; padding:20px; border-radius:15px 15px 0 0; }
    .table thead th { background:#f8f9fa; font-weight:600; }
    .btn-primary { background:linear-gradient(135deg,#667eea,#764ba2); border:none; }
    .btn-danger { background:linear-gradient(135deg,#f093fb,#f5576c); border:none; }
    .search-box { border-radius:25px; padding:10px 20px; border:2px solid #e9ecef; }
    .modal-header { background:linear-gradient(135deg,#667eea,#764ba2); color:white; }
    tbody tr:hover { background:#f8f9fa; }
  </style>
</head>

<body>
<div class="container-fluid">
  <div class="card">

    <!-- HEADER -->
    <div class="card-header">
      <h3 class="mb-0"><i class="bi bi-house-door"></i> Danh sách phòng trọ</h3>
    </div>

    <div class="card-body">

      <!-- ALERT -->
      <c:if test="${param.success eq 'true'}">
        <div class="alert alert-success">Thêm phòng trọ thành công!</div>
      </c:if>

      <c:if test="${param.deleteSuccess eq 'true'}">
        <div class="alert alert-success">Xóa phòng trọ thành công!</div>
      </c:if>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <!-- SEARCH + BUTTON -->
      <div class="row mb-4">
        <div class="col-md-6">
          <form action="${pageContext.request.contextPath}/phong-tro" method="get" class="d-flex">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" class="form-control search-box me-2"
                   value="${keyword}" placeholder="Tìm theo mã, tên hoặc số điện thoại...">
            <button class="btn btn-primary"><i class="bi bi-search"></i></button>
          </form>
        </div>

        <div class="col-md-6 text-end">
          <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
            <i class="bi bi-plus-circle"></i> Tạo mới
          </button>
          <button class="btn btn-danger" onclick="confirmDelete()">
            <i class="bi bi-trash"></i> Xóa
          </button>
        </div>
      </div>

      <!-- TABLE -->
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
          <tr>
            <th><input type="checkbox" id="selectAll" class="form-check-input"></th>
            <th>STT</th>
            <th>Mã phòng</th>
            <th>Tên người thuê</th>
            <th>SĐT</th>
            <th>Ngày bắt đầu</th>
            <th>Hình thức</th>
            <th>Ghi chú</th>
          </tr>
          </thead>

          <tbody>
          <c:forEach var="pt" items="${listPhongTro}" varStatus="st">
            <tr>
              <td>
                <input type="checkbox" class="form-check-input row-checkbox"
                       value="${pt.maPhongTro}" data-ma="${pt.maPhongTroFormatted}">
              </td>

              <td>${st.index + 1}</td>
              <td><span class="badge bg-info">${pt.maPhongTroFormatted}</span></td>
              <td>${pt.tenNguoiThue}</td>
              <td>${pt.soDienThoai}</td>

              <td>
                <fmt:formatDate value="${pt.ngayBatDauDate}" pattern="dd-MM-yyyy"/>
              </td>

              <td><span class="badge bg-success">${pt.tenHinhThuc}</span></td>

              <td>${empty pt.ghiChu ? '...' : pt.ghiChu}</td>
            </tr>
          </c:forEach>

          <c:if test="${empty listPhongTro}">
            <tr>
              <td colspan="8" class="text-center text-muted">Không có dữ liệu</td>
            </tr>
          </c:if>
          </tbody>
        </table>
      </div>

    </div>
  </div>
</div>

<!-- MODAL ADD -->
<div class="modal fade" id="addModal">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">

      <div class="modal-header">
        <h5 class="modal-title">Tạo thông tin thuê trọ</h5>
        <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>

      <form method="post" action="${pageContext.request.contextPath}/phong-tro">
        <input type="hidden" name="action" value="add">

        <div class="modal-body">
          <label class="form-label">Tên người thuê *</label>
          <input type="text" name="tenNguoiThue" class="form-control" value="${tenNguoiThue}" required>

          <label class="form-label mt-3">Số điện thoại *</label>
          <input type="text" name="soDienThoai" class="form-control" value="${soDienThoai}" required>

          <label class="form-label mt-3">Ngày bắt đầu *</label>
          <input type="text" name="ngayBatDau" placeholder="dd-mm-yyyy"
                 class="form-control" value="${ngayBatDau}" required>

          <label class="form-label mt-3">Hình thức thanh toán *</label>
          <select name="maHinhThuc" class="form-select" required>
            <option value="">-- Chọn --</option>
            <c:forEach var="ht" items="${listHinhThuc}">
              <option value="${ht.maHinhThuc}"
                      <c:if test="${maHinhThuc == ht.maHinhThuc}">selected</c:if>>
                  ${ht.tenHinhThuc}
              </option>
            </c:forEach>
          </select>

          <label class="form-label mt-3">Ghi chú</label>
          <textarea name="ghiChu" maxlength="200" class="form-control">${ghiChu}</textarea>
        </div>

        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
          <button class="btn btn-primary">Tạo mới</button>
        </div>
      </form>

    </div>
  </div>
</div>

<!-- MODAL DELETE -->
<div class="modal fade" id="deleteModal">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">

      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title">Xóa phòng trọ</h5>
        <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>

      <form id="deleteForm" method="post" action="${pageContext.request.contextPath}/phong-tro">
        <input type="hidden" name="action" value="delete">

        <div class="modal-body">
          <p id="deleteMessage"></p>
        </div>

        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal">Không</button>
          <button class="btn btn-danger">Có</button>
        </div>
      </form>

    </div>
  </div>
</div>

<!-- SCRIPT -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  document.getElementById("selectAll").onclick = function () {
    document.querySelectorAll(".row-checkbox").forEach(cb => cb.checked = this.checked);
  };

  function confirmDelete() {
    const checkboxes = document.querySelectorAll('.row-checkbox:checked');

    if (checkboxes.length === 0) {
      alert('Vui lòng chọn ít nhất một phòng trọ để xóa!');
      return;
    }

    const ids = [];
    checkboxes.forEach(cb => ids.push(cb.getAttribute('data-ma')));

    document.getElementById("deleteMessage").innerText =
            "Bạn có muốn xóa " + ids.join(", ") + " không?";

    const deleteForm = document.getElementById("deleteForm");

    // XÓA input selectedIds cũ, nhưng KHÔNG xoá nội dung form!
    deleteForm.querySelectorAll("input[name='selectedIds']").forEach(e => e.remove());

    // THÊM input selectedIds mới
    checkboxes.forEach(cb => {
      let input = document.createElement("input");
      input.type = "hidden";
      input.name = "selectedIds";
      input.value = cb.value;
      deleteForm.appendChild(input);
    });

    // SHOW modal
    new bootstrap.Modal(document.getElementById('deleteModal')).show();
  }

</script>

</body>
</html>
