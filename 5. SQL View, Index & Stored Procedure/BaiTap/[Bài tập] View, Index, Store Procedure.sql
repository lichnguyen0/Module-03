-- [Bài tập] View, Index, Store Procedure

-- tạo CSDL Demmo
create database Demmo;

use demmo;

-- tạo bảng Products

create table Products (
Id int primary key,
productCode VARCHAR(20) NOT NULL,
    productName VARCHAR(100) NOT NULL,
    productPrice DECIMAL(10,2) NOT NULL,
    productAmount INT DEFAULT 0,
    productDescription TEXT,
    productStatus VARCHAR(50)
); 

-- [Bài tập] View, Index, Store Procedure

-- tạo CSDL Demmo
create database Demmo;

use demmo;

-- tạo bảng Products

create table Products (
Id int primary key,
productCode VARCHAR(20) NOT NULL,
    productName VARCHAR(100) NOT NULL,
    productPrice DECIMAL(10,2) NOT NULL,
    productAmount INT DEFAULT 0,
    productDescription TEXT,
    productStatus VARCHAR(50)
); 

-- chèn dữ liệu mẫu
INSERT INTO Products (Id, productCode, productName, productPrice, productAmount, productDescription, productStatus)
VALUES 
(1, 'P001', 'Laptop Asus TUF', 1500.00, 10, 'Laptop gaming hiệu năng cao', 'Available'),
(2, 'P002', 'iPhone 15 Pro', 1200.00, 5, 'Điện thoại cao cấp của Apple', 'Available'),
(3, 'P003', 'Samsung Smart TV 55"', 800.00, 7, 'Smart TV độ phân giải 4K', 'Out of stock'),
(4, 'P004', 'Sony WH-1000XM5', 350.00, 15, 'Tai nghe chống ồn cao cấp', 'Available'),
(5, 'P005', 'Dell Inspiron 15', 900.00, 12, 'Laptop văn phòng mỏng nhẹ', 'Available');

-- Bước 3 tạo index
CREATE UNIQUE INDEX idx_product_code ON Products(productCode);
/*
• CREATE UNIQUE INDEX : Tạo chỉ mục duy nhất (unique index).
   Nó đảm bảo không có 2 hàng nào trong bảng có cùng giá trị ở cột được đánh chỉ mục.
   
• idx_product_code : Tên chỉ mục.theo quy tắc: idx_<tên_bảng>_<tên_cột> để dễ nhớ.

• ON Products(productCode): Chỉ định chỉ mục được tạo trên bảng Products, cột productCode.
*/

-- Tạo Composite Index (chỉ mục kết hợp nhiều cột)
CREATE INDEX idx_product_name_price ON Products(productName, productPrice);
/*
• CREATE INDEX : tạo mục thông thường
• idx_product_name_price : Tên chỉ mục. đặt theo dạng idx_<bảng>_<cột1>_<cột2> để dễ nhớ.
• ON Products(productName, productPrice) : Chỉ định chỉ mục được tạo trên bảng Products,
 và bao gồm 2 cột là productName và productPrice.
*/


-- Kiểm tra bằng EXPLAIN trước và sau khi tạo Index
-- trước khi tạo 
EXPLAIN SELECT * FROM Products WHERE productCode = 'P001';
-- EXPLAIN được dùng để phân tích cách MySQL thực thi câu truy vấn.
-- Nó cho bạn biết MySQL có đang sử dụng index hay không, đang quét bao nhiêu dòng, và hiệu quả truy vấn ra sao.


-- sau khi tạo
EXPLAIN SELECT * FROM Products WHERE productCode = 'P001';
-- không trả về dữ liệu sản phẩm, mà sẽ cho bạn thông tin MySQL dùng cách nào để truy vấn.

-- bước 4 : thuc hanh view

-- tao view
CREATE VIEW view_products AS
SELECT productCode, productName, productPrice, productStatus
FROM Products;

/* 
- CREATE VIEW : Tạo một khung nhìn (view) bảng ảo view
- view_products : Tên của View tự mình tạo.
- AS : Dùng để chỉ định câu lệnh SELECT sẽ tạo nội dung cho View.
- SELECT ... FROM Products : truy vấn nguồn dữ liệu mà View “nhìn” vào trong bảng Products.
*/

-- Kiểm tra view
SELECT * FROM view_products;
-- lấy toàn bộ dữ liệu từ View view_products


-- Sửa view
-- ví dụ thêm productAmount
CREATE OR REPLACE VIEW view_products AS
SELECT productCode, productName, productPrice, productStatus, productAmount 
FROM Products;

/*
• CREATE OR REPLACE VIEW: 

• Nếu View view_products chưa tồn tại → MySQL sẽ tạo mới View.

• Nếu View view_products đã tồn tại → MySQL sẽ ghi đè (thay thế) View cũ bằng nội dung mới.
*/


-- xoá view
DROP VIEW view_products;


-- Bước 5: Stored Procedure

-- lấy tất cả sản phẩm
DELIMITER $$

CREATE PROCEDURE getAllProducts() 
BEGIN
    SELECT * FROM Products;
END $$

DELIMITER ;

-- CREATE PROCEDURE tạo ra một thủ tục lưu trữ (stored procedure).
-- getAllProducts: là tên của thủ tục (tên tự đặt).
-- (): vì thủ tục này không có tham số (không có đầu vào hay đầu ra).
-- SELECT * FROM Products; : Đây là nội dung thực thi của thủ tục.

-- goi 
CALL getAllProducts();


-- 2. Thêm một sản phẩm mới
DELIMITER $$

CREATE PROCEDURE addProduct(
    IN pCode VARCHAR(20),
    IN pName VARCHAR(100),
    IN pPrice DECIMAL(10,2),
    IN pAmount INT,
    IN pDesc TEXT,
    IN pStatus VARCHAR(20)
)
BEGIN
    INSERT INTO Products (productCode, productName, productPrice, productAmount, productDescription, productStatus)
    VALUES (pCode, pName, pPrice, pAmount, pDesc, pStatus);
END $$

DELIMITER ;

-- goi
CALL addProduct('P005', 'MacBook Air', 1300.00, 8, 'Apple laptop', 'Available');


-- Sửa sản phẩm theo Id
DELIMITER $$

CREATE PROCEDURE updateProductById(
    IN pId INT,
    IN pName VARCHAR(100),
    IN pPrice DECIMAL(10,2),
    IN pAmount INT,
    IN pDesc TEXT,
    IN pStatus VARCHAR(20)
)
BEGIN
    UPDATE Products
    SET productName = pName,
        productPrice = pPrice,
        productAmount = pAmount,
        productDescription = pDesc,
        productStatus = pStatus
    WHERE Id = pId;
END $$

DELIMITER ;

-- goi
CALL updateProductById(2, 'iPhone 15 Pro', 1400.00, 4, 'Upgraded Apple smartphone', 'Available');


-- Xoá sản phẩm theo Id
DELIMITER $$

CREATE PROCEDURE deleteProductById(IN pId INT)
BEGIN
    DELETE FROM Products WHERE Id = pId;
END $$

DELIMITER ;

-- goi
CALL deleteProductById(4);





