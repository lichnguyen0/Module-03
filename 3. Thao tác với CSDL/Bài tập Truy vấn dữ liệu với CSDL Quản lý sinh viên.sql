use QuanLySinhVien;

SELECT *
FROM student
WHERE studentName LIKE 'h%';

SELECT *
FROM class
WHERE MONTH(StartDate) = 12;

SELECT *
FROM subject
WHERE credit BETWEEN 3 AND 5;

UPDATE student
SET ClassId = 2
WHERE StudentId = 5;  -- thay 5 bằng ID của sinh viên 'Hung'


SELECT st.StudentName, sub.SubName, m.Mark
FROM student st
JOIN mark m ON st.StudentId = m.StudentId
JOIN subject sub ON sub.SubId = m.SubId
ORDER BY m.Mark DESC, st.StudentName ASC;


