CREATE DATABASE IF NOT EXISTS student_attendance;
USE student_attendance;

/*Create Student table*/
CREATE TABLE IF NOT EXISTS student(
                                      id VARCHAR(30) PRIMARY KEY ,
                                      name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS attendance(
                                         attendance_id INT AUTO_INCREMENT,
                                         student_id VARCHAR(30) NOT NULL ,
                                         date DATE NOT NULL,
                                         status ENUM ('IN','OUT'),
                                         CONSTRAINT pk_attendance PRIMARY KEY (attendance_id),
                                         CONSTRAINT fk_attendance FOREIGN KEY (student_id) REFERENCES student(id)
);


CREATE TABLE IF NOT EXISTS user(
    user_name VARCHAR(30)
);