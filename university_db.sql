CREATE SCHEMA `university`;

-- Drap tables
drop table university.teacher;
drop table university.student_subject;
drop table university.student_club;
drop table university.student;
drop table university.person;
drop table university.ref_subjects;
drop table university.ref_clubs;


-- Create tables
create table university.ref_subjects (
	subject_id int not null auto_increment,
    subject_name varchar(50) not null,    
    primary key(subject_id)    
);

create table university.ref_clubs (
	club_id int not null auto_increment,
    club_name varchar(50) not null,    
    primary key(club_id)    
);

create table university.person (
	person_id int NOT NULL AUTO_INCREMENT,
    last_name varchar(50) not null,
    first_name varchar(30) not null,
    birthday date not null,
    primary key(person_id),
    unique UQ_PERSON_1 (first_name, last_name, birthday)
);

create table university.teacher (
	person_id int not null,
    position varchar(50) not null,
    subject_id int,
        
    primary key(person_id),
    constraint FK_TEACHER_1 foreign key (person_id) references person(person_id),
    constraint FK_TEACHER_2 foreign key (subject_id) references ref_subjects(subject_id)
);

create table university.student (
	person_id int not null,
                    
    primary key(person_id),
    constraint FK_STUDENT_PERSON foreign key (person_id) references person(person_id)
);

create table university.student_subject (
	student_id int not null,
    subject_id int not null,
    
	primary key(student_id, subject_id),
    constraint FK_SS_STUDENT foreign key (student_id) references student(person_id),
    constraint FK_SS_SUBJECT foreign key (subject_id) references ref_subjects(subject_id)    
);


create table university.student_club (
	student_id int not null,
    club_id int not null,
    
    primary key(student_id, club_id),
    constraint FK_SC_STUDENT foreign key (student_id) references student(person_id),
    constraint FK_SC_CLUB foreign key (club_id) references ref_clubs(club_id)    
);



# ------------------------------------------------------------------------- 
-- Populate dictionaries
insert into university.ref_subjects value(1, "Biology");
insert into university.ref_subjects value(2, "Math");
insert into university.ref_subjects value(3, "Programming");
insert into university.ref_subjects value(4, "History");
insert into university.ref_subjects value(5, "Phisics");

insert into university.ref_clubs value(1, "Football");
insert into university.ref_clubs value(2, "Chess");
insert into university.ref_clubs value(3, "Programming");