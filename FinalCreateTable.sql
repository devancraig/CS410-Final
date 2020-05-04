# drop table class;
-- Create database CS410FinalProject;
use heroku_ffcbf3ee2ade5c2;
-- drop table categories;

-- DROP TABLE grades;
/*
DROP TABLE assignments;
DROP TABLE categories;
drop table class;
*/


create table class (
	class_id int primary KEY AUTO_INCREMENT not null,
    course_number varchar(10),
    term varchar(10),
    section_number int,
    description text
);
 
create table categories (
	 cat_id int primary KEY AUTO_INCREMENT not null,
    class_id int not null,
    name varchar(20),
    weight float,
    foreign key (class_id)
		references class(class_id)
);

   
create table assignments (
	 assign_id int primary KEY AUTO_INCREMENT not null, 
	 cat_id INT NOT NULL,
    name varchar(250),
    description text,
    point_value int,
   
	INDEX (cat_id),
    
	foreign key (cat_id)
		references categories(cat_id)
);


create table students (
	s_id int primary KEY AUTO_INCREMENT not NULL,
    class_id INT not null,
    StudentID INT,
    username varchar(250),
	firstname varchar(250),
	lastname VARCHAR(250),
   FOREIGN KEY (class_id) REFERENCES class (class_id),
	INDEX (class_id)
);

create table grades (
	grade_id int not null,
	assign_id int not null,
    s_id int not null,
    letter varchar(2),
    grade float,
  	PRIMARY KEY(grade_id),
    INDEX (assign_id),
    INDEX (s_id),
    
    foreign key (assign_id)
		references assignments(assign_id),
	foreign key (s_id)
		references students(s_id)

);



select * from grades;
select * from students;
select * from assignments;
select * from class;