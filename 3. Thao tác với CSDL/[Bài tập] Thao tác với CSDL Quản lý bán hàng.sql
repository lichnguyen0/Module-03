use QuanLyBanHang;

-- thêm dữ liệu
-- sử dụng insert

insert into Customer values
(1, 'Minh Quan', 10),
(2, 'Ngoc oanh', 20),
(3,'Hong Ha', 50);

insert into Product values
(1, 'May Giat', 3),
(2, 'Tu Lanh', 5),
(3, 'Dieu Hoa', 7),
(4, 'Quat', 1),
(5, 'Biep Dien', 2);

insert into `Order` values
(1, 1, '2006-03-21', null),
(2, 2, '2006-03-23', null),
(3,1,'2006-03-16', null);


insert into OrderDetail values
(1,1,3),
(1,3,7),
(1,4,2),
(2,1,1),
(2,5,1),
(3,1,8),
(3,3,4);


-- hien thi oID, oDate, oTotalPrice của tất cả hoá đơn
-- dung select  để lấy dữ liệu từ bảng trong cơ sở dữ liệu
-- ví dụ như xem danh sách khách hàng, xem hoá đơn, lọc dữ liệu theo ngày...vv
select oID, oDate, oTotalPrice
from `Order`;

-- hien thi danh sach khach hang da mua hang va san pham duoc mua
select distinct 
c.cName as 'Khach Hang', -- lấy tên khác hàng(cName) và đặt tên cột hiện thị là 'khach hang'
p.pname as 'San Pham' --  lấy tên khác hàng(pName) và đặt tên cột hiện thị là 'San Pham'
from Customer c -- bảng customer đặt bí danh là c
join `Order` o  -- nối bảng Order và đặt bí danh là o
  ON c.cID = o.cID -- Điều kiện nối: mã khách hàng (cID) trong Customer trùng với cID trong Order
join OrderDetail od -- Nối tiếp với bảng OrderDetail, đặt bí danh là od
  ON o.oID = od.oID -- Điều kiện nối: mã hóa đơn (oID) trong Order trùng với oID trong OrderDetail
join Product p -- Nối thêm với bảng Product, đặt bí danh là p
  ON od.pID = p.pID; -- Điều kiện nối: mã sản phẩm (pID) trong OrderDetail trùng với pID trong Product
  
  
-- hien ten nhung khach hang khong mua bat ky san pham nào
select c.cName -- lấy tên khách hàng từ bảng customer
from Customer c -- từ bảng customer đặt tên bí danh là c
left join `Order` o  -- dùng bảng left join để nối và đặt tên bí dạn là o
ON c.cID = o.cID -- điều kiện nối: mã khách hàng trong customer trùng với mã khách hàng trong order
WHERE o.oID IS NULL; -- Chỉ lấy những khách hàng KHÔNG có đơn hàng (vì khi không khớp, o.oID sẽ bị NULL)

-- hiển thị mã hoá đơn, ngày bán và giá tiền của từng hoá đơn
SELECT 
    o.oID AS 'Ma Hoa Don', -- Lấy mã hóa đơn (oID) từ bảng Order và đặt tên hiển thị là "Ma Hoa Don"
    o.oDate AS 'Ngay Ban', -- Lấy ngày bán hàng (oDate) và đặt tên hiển thị là "Ngay Ban"
    SUM(od.odQTY * p.pPrice) AS 'Tong Gia Tri Hoa Don'   -- Tính tổng tiền = số lượng sản phẩm * giá sản phẩm
FROM `Order` o    -- Lấy dữ liệu từ bảng Order, đặt bí danh là o
JOIN OrderDetail od ON o.oID = od.oID  -- Nối với bảng OrderDetail qua mã hóa đơn (oID) để lấy chi tiết từng sản phẩm trong hóa đơn
JOIN Product p ON od.pID = p.pID  -- Nối tiếp với bảng Product qua mã sản phẩm (pID) để biết giá của từng sản phẩm
GROUP BY o.oID, o.oDate; -- Gom nhóm theo mã hóa đơn và ngày bán để tính tổng cho từng hóa đơn riêng

  
  
  
