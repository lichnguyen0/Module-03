-- [Thực hành] Store Procedure (v.2023)

use classicmodels

DELIMITER  $$

create PROCEDURE findAllCustomers()

begin
select * from customers;
end $$

DELIMITER ;

/* giải thích :
• DELIMITER // dùng để phân cách bộ nhớ lưu trữ thủ tục Cache và mở ra một ô lưu trữ mới.
• CREATE PROCEDURE findAllCustomers() dùng để khai báo tạo một Procedure mới, trong đó findAllCustomers chính là tên thủ tục còn hai từ đầu là từ khóa.
• **BEGIN và END// ** dùng để khai báo bắt đầu của Procedure và kết thúc Procedure
• **DELIMITER ; ** đóng lại ô lưu trữ
*/

--  gọi Procedure
call findAllCustomers()

-- sử procedure
-- Trong Mysql không cung cấp lệnh sửa Stored nên thông thường chúng ta sẽ chạy lệnh tạo mới.
-- Lệnh Drop để xóa đi Procedure đó và tạo lại:

DELIMITER $$ 
drop PROCEDURE if exists `findAllCustomers` $$

create PROCEDURE findAllCustomers()

begin 

select * from customers where customerNumber = 175;

end $$