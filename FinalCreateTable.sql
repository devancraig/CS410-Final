# drop table class;
-- Create database CS410FinalProject;
use heroku_ffcbf3ee2ade5c2;
-- drop table categories;
-- drop table class;

create table class (
	class_id int primary key not null,
    course_number varchar(10),
    term varchar(10),
    section_number int,
    description varchar (200)
);
  
create table categories (
	cat_id int not null,
    class_id int not null,
    name varchar(20),
    weight float,
    PRIMARY KEY(cat_id),
    foreign key (class_id)
		references class(class_id)
);
    
create table assignments (
	assign_id int not null,
    class_id int not null,
    cat_id int not null,
    name varchar(250),
    description text,
    point_value float,
    
	PRIMARY KEY(assign_id),
    INDEX (class_id),
    INDEX (cat_id),
    
    foreign key (class_id)
		references class(class_id),
	foreign key (cat_id)
		references categories(cat_id)
);

create table students (
	student_id int,
    class_id int,
    username varchar(250),
	name varchar(250),
	PRIMARY KEY(student_id),
    INDEX (class_id),
    
    foreign key (class_id)
		references class(class_id)
);

create table grades (
	grade_id int not null,
	assign_id int not null,
    student_id int not null,
    letter varchar(2),
    grade float,
  	PRIMARY KEY(grade_id),
    INDEX (assign_id),
    INDEX (student_id),
    
    foreign key (assign_id)
		references assignments(assign_id),
	foreign key (student_id)
		references students(student_id)

);

select * from grades;
select * from students;
select * from assignments;
select * from class;