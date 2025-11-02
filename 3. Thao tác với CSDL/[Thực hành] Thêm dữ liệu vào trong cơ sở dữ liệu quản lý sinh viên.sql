use quanlysinhvien;

/* them luan luot cac dòng vao class
 để thêm dữ liêu vào bảng bằng cú pháp:
câu lệnh insert into + tên bảng*/

insert into class
values (1, 'A1', '2008-12-20', 1); -- sử dụng từ khoá values để chỉ ra các giá trị sẽ được chèn vào bảng
insert into class
values(2, 'A2', '2008-12-22', 1);
insert into class
values(3, 'B3', CURRENT_DATE(), 0); -- current_date là một hàm (function) trả về ngày hiện tại của hệ thống


-- THÊM DỮ LIÊU JVÀO BẢNG SINH VIÊN
insert into student(StudentName, Address, Phone, Status, ClassId)
VALUES ('Hung', 'Ha Noi', '0912113113', 1, 1);

INSERT INTO student (StudentName, Address, Status, ClassId)
VALUES ('Hoa', 'Hai phong', 1, 1);

INSERT INTO Student (StudentName, Address, Phone, Status, ClassId)
VALUES ('Manh', 'HCM', '0123123123', 0, 2);

-- thêm dữ liệu nhanh vào trong bảng object
insert into subject -- đây là cách là chèn nhiều dòng một lúc thông qua dấu ','
					-- không cần phải nhập từng cái một
					-- phù hợp khi bảng không quá lớn.
value (1, 'CF',5,1),
 (2, 'C', 6,1),
 (3, 'HDJ', 5, 1),
 (4, 'RDBMS', 10, 1);
 
 
 -- thêm dữ liệu vào mark
 insert into mark (SubID, StudentID, Mark, ExamTimes)
 value(1,1,8,1),
 (1,2,10,2),
 (2,1,12,1);