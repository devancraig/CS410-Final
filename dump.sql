
/* add a class
INSERT INTO class (course_number, term, section_number, description) VALUES ('CS 410/510', 'Spring', 001, 'Foundations of database management systems. Database models: relational, object and others. Database design: entity-relationship modeling, logical relational schema design, physical design, functional dependencies and normalization, and database tuning. Database application development using database interfaces embedded in host languages.');
*/

/* add students
INSERT INTO students (student_id, class_id, username, NAME) VALUES (1, 11, 'devancraig', 'Devan Craig');
INSERT INTO students (student_id, class_id, username, NAME) VALUES (2, 11, 'tannerhalcumb', 'Tanner Halcumb');
*/


 /* add categories
INSERT INTO categories (cat_id, class_id, NAME, weight) VALUES (1, 11, 'Exams', 30); 
INSERT INTO categories (cat_id, class_id, NAME, weight) VALUES (2, 11, 'Final', 20);
INSERT INTO categories (cat_id, class_id, NAME, weight) VALUES (3, 11, 'Homework', 35);
INSERT INTO categories (cat_id, class_id, NAME, weight) VALUES (4, 11, 'Final Project', 15);
*/


/* add assignments
INSERT INTO assignments (assign_id, class_id, cat_id, NAME, description, point_value) VALUES (1, 3, 'Assignment 1', 'A bike shop needs a database to manage their sales of parts and service. Here are some of the things they need to track', 100);
INSERT INTO assignments (assign_id, class_id, cat_id, NAME, description, point_value) VALUES (2, 1, 'Midterm 1', 'Transactions', 100);
*/


/* add grades
INSERT INTO grades (grade_id, assign_id, student_id, letter, grade) VALUES (1, 1, 1, 'A', 95);
INSERT INTO grades (grade_id, assign_id, student_id, letter, grade) VALUES (2, 2, 1, 'B', 81);
INSERT INTO grades (grade_id, assign_id, student_id, letter, grade) VALUES (3, 1, 2, 'A', 99);
INSERT INTO grades (grade_id, assign_id, student_id, letter, grade) VALUES (4, 2, 2, 'B', 84);
*/





/*
delimiter //

CREATE PROCEDURE newclass (cnum varchar(10), t varchar(10), snum INT, descript TEXT)
BEGIN
	INSERT INTO class (course_number, term, section_number, description) VALUES (cnum, t, snum, descript);
END //

Delimiter ; 
*/

/*
delimiter //

CREATE PROCEDURE addstudent (uname VARCHAR(250), sID INT, l VARCHAR(250), f VARCHAR(250), cID INT)
BEGIN
	INSERT INTO students (username, StudentID, lastname, firstname, class_id) VALUES (uname, sID, l, f, cID);
END //

Delimiter ; 

*/

/*
delimiter //

CREATE PROCEDURE addAssignment ()
BEGIN
	INSERT INTO students (username, StudentID, lastname, firstname, class_id) VALUES (uname, sID, l, f, cID);
END //

Delimiter ; 
*/


-- CALL addstudent('devancraig', 1242, 'devan', 'devan', 21);
SELECT class_id FROM class WHERE term LIKE '%Sp%20%';

SELECT * FROM categories;
select * from assignments;
select * from grades;
select * from students;
select * from class;
/*
SELECT * FROM class c
LEFT JOIN students s ON c.class_id = s.class_id
*/
