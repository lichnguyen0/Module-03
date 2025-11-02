use QuanLySinhVien;

-- Môn học có credit lớn nhất

SELECT *
FROM subject
WHERE credit = (SELECT MAX(credit) FROM subject);

-- Môn học có điểm thi cao nhất
SELECT s.*
FROM subject s
JOIN mark m ON s.SubId = m.SubId
WHERE m.Mark = (SELECT MAX(Mark) FROM mark)
LIMIT 0, 1000;


-- Thông tin sinh viên và điểm trung bình, xếp theo điểm giảm dần
SELECT st.StudentId, st.StudentName, AVG(m.Mark) AS avg_score
FROM student st
JOIN mark m ON st.StudentId = m.StudentId
GROUP BY st.StudentId, st.StudentName
ORDER BY avg_score DESC
LIMIT 0, 1000;

