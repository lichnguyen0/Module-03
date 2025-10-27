-- [Thực hành] Chỉ mục trong MySql (v.2023)

--  để tìm thông tin của khách hàng có tên là Land Of Toys Inc., bạn sẽ query như sau:

select * from customers where customersName = 'Land of Toys Inc.';

explain select * from customers where customerName = 'Land of Toys Inc.';

alter table customers add index idx_customerName(customerName);
EXPLAIN SELECT * FROM customers WHERE customerName = 'Land of Toys Inc.';
/*
• possible_keys : Đưa ra những Index có thể sử dụng để query
• key : và Index nào đang được sử dụng
• key_len : Chiều dài của từng mục trong Index
• ref : Cột nào đang sử dụng
• rows : Số hàng (rows) mà MySQL dự đoán phải tìm
• extra : Thông tin phụ, thật tệ nếu tại cột này là “using temporary” hay “using filesort”
*/

alter table customers add index isx_full_name(contactFirstName, contactLastName);
explain select * from customers where contactFirstName = 'jean' or contactFirstName = 'King';

-- Để xoá chỉ mục trong bảng, bạn làm 
ALTER TABLE customers DROP INDEX idx_full_name;