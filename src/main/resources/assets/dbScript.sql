DROP DATABASE IF EXISTS dep8_student_attendance;
CREATE DATABASE dep8_student_attendance;
USE dep8_student_attendance;

/*Create attendance table*/
CREATE TABLE student
(
    id               VARCHAR(30) PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    picture          LONGBLOB     NOT NULL,
    guardian_contact VARCHAR(15)  NOT NULL
);

CREATE TABLE user
(
    user_name VARCHAR(30) PRIMARY KEY,
    name      VARCHAR(100)          NOT NULL,
    password  VARCHAR(50)           NOT NULL,
    role      ENUM ('ADMIN','USER') NOT NULL
);

/*Create Student table*/
CREATE TABLE attendance
(
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    date          DATETIME              NOT NULL,
    status        ENUM ('IN','OUT') NOT NULL,
    student_id    VARCHAR(30)       NOT NULL,
    username      VARCHAR(30)       NOT NULL,
    CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES student (id),
    CONSTRAINT fk_attendance_user FOREIGN KEY (username) REFERENCES user (user_name)
);

/*Have to modify the date column into DATETIME*/
ALTER TABLE attendance MODIFY COLUMN date DATETIME;

/*Delete the data in the attendance table*/
/*TRUNCATE TABLE attendance;*/



ALTER TABLE student DROP COLUMN guardian_contact;
ALTER TABLE student ADD COLUMN guardian_contact VARCHAR(15) NOT NULL ;
ALTER TABLE student ADD CONSTRAINT uk_student UNIQUE (guardian_contact);


SELECT * FROM attendance;
SELECT status FROM attendance WHERE student_id='2021/DEP08/1001' ORDER BY date DESC LIMIT 1;

SELECT attendance.student_id, attendance.date, attendance.status, student.name FROM attendance
    INNER JOIN student ON attendance.student_id = student.id ORDER BY date DESC LIMIT 1;





