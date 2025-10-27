-- [Thực hành] Truyền tham số vào Store Procedure (v.2023)

use classicmodels;

-- tham số loại IN

delimiter $$ 

create PROCEDURE getCusById

(IN cusNum INT (11))

begin

select * from customers where customerNumber = cusNum;

end $$

delimiter ;


-- gọi store procedure
call getCusById(175);

-- tham số loại OUT 

delimiter $$

create PROCEDURE GetCustomersCountByCity(

In in_city varchar(50),

out total int

)

begin

select count(customerNumber)

into total

from customers

where city = in_city;

end $$

delimiter ;

-- gọi store procedure:

call GetCustomersCountByCity('Lyon', @total);

SELECT @total;

-- tham so loai INOUT	
DELIMITER //

INOUT counter INT,

IN inc INT

)

BEGIN

SET counter = counter + inc;

END //

DELIMITER ;

-- goi store procedure

SET @counter = 1;

CALL SetCounter(@counter,1); -- 2

CALL SetCounter(@counter,1); -- 3

CALL SetCounter(@counter,5);-- 8

SELECT @counter; -- 8