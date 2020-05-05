
delimiter //

CREATE PROCEDURE newclass (cnum varchar(10), t varchar(10), snum INT, descript TEXT)
BEGIN
	INSERT INTO class (course_number, term, section_number, description) VALUES (cnum, t, snum, descript);
END //

Delimiter ; 



delimiter //

CREATE PROCEDURE addstudent (uname VARCHAR(250), sID INT, l VARCHAR(250), f VARCHAR(250), cID INT)
BEGIN
	INSERT INTO students (username, StudentID, lastname, firstname, class_id) VALUES (uname, sID, l, f, cID);
END //

Delimiter ; 





delimiter //

CREATE PROCEDURE addassignment (c_id int, n VARCHAR(250), descript TEXT, pv FLOAT)
BEGIN
	INSERT INTO assignments(cat_id, NAME, description, point_value) VALUES (c_id, n, descript, pv);
END //

Delimiter ; 



delimiter //

CREATE PROCEDURE addcategories (c_id INT, n VARCHAR(250), w float)
BEGIN
	INSERT INTO categories(class_id, NAME, weight) VALUES (c_id, n, w);
END //

Delimiter ; 





delimiter //

CREATE PROCEDURE showassignments (c VARCHAR(250))
BEGIN
	SELECT assignments.name, assignments.description, assignments.point_value, categories.name AS category FROM assignments
	left JOIN categories ON assignments.cat_id = categories.cat_id
	LEFT JOIN class ON categories.class_id = class.class_id
	WHERE class.course_number = c
	GROUP BY categories.name;
END //

Delimiter ; 



delimiter //

CREATE PROCEDURE getassignid (n VARCHAR(250), c_id INT)
BEGIN
	SELECT a.assign_id FROM assignments a
	LEFT JOIN categories ca ON a.cat_id = ca.cat_id
	LEFT JOIN class c ON ca.class_id = c.class_id
	WHERE a.NAME = n AND c.class_id = c_id;
END //

Delimiter ; 



delimiter //

CREATE PROCEDURE addgrade (a_id INT, sID INT, l VARCHAR(250), g float)
BEGIN
	INSERT INTO grades(assign_id, s_id, letter, grade) VALUES (a_id, sID, l, g);
END //

Delimiter ; 



delimiter //

CREATE PROCEDURE studentgrade (uname VARCHAR(250), c_id INT)
BEGIN
	SELECT ca.name AS catName,a.name AS assignName, ca.weight, a.point_value, g.grade FROM students s
	LEFT JOIN class c ON s.class_id = c.class_id
	LEFT JOIN categories ca ON s.class_id = ca.class_id
	LEFT JOIN assignments a ON ca.cat_id = a.cat_id
	LEFT JOIN grades g ON a.assign_id = g.assign_id
	WHERE s.username = uname AND ca.class_id = c_id 
	GROUP BY ca.name;
END //

Delimiter ; 


CREATE VIEW finalgradebook AS 
SELECT  
	catName,
	SUM(point_value) AS pv,
	SUM(grade) AS grade,
	((SUM(grade) / SUM(point_value)) * weight) AS total
 FROM student_grades
 GROUP BY catName;
 
 


 delimiter //

CREATE PROCEDURE studentfinalgrade (c_id INT)
BEGIN
		SELECT  
		catName,
		SUM(point_value) AS pv,
		SUM(grade) AS grade,
		((SUM(grade) / SUM(point_value)) * weight) AS total
	 FROM igrades
	 WHERE class_id = c_id
	 GROUP BY catName;
END //

Delimiter ; 



