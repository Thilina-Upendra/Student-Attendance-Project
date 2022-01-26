DROP DATABASE IF EXISTS dep8_student_attendance;
CREATE DATABASE dep8_student_attendance;
USE dep8_student_attendance;

/*Create attendance table*/
CREATE TABLE student
(
    id      VARCHAR(30) PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    picture LONGBLOB     NOT NULL
);

CREATE TABLE user
(
    user_name VARCHAR(30) PRIMARY KEY,
    name      VARCHAR(100)          NOT NULL,
    password  VARCHAR(50)           NOT NULL,
    rate      ENUM ('ADMIN','USER') NOT NULL
);

/*Create Student table*/
CREATE TABLE attendance
(
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    date          DATE              NOT NULL,
    status        ENUM ('IN','OUT') NOT NULL,
    student_id    VARCHAR(30)       NOT NULL,
    username      VARCHAR(30)       NOT NULL,
    CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES student (id),
    CONSTRAINT fk_attendance_user FOREIGN KEY (username) REFERENCES user (user_name)
);


