select * from patient p;
select * from doctor d;
select * from users u;
select * from pharmacist ph;
select * from prescription pr;
select * from appointment a;

create table if not exists patient (
    id bigint primary key,
    first_name varchar(20) not null,
    last_name varchar(40) not null,
    email varchar(40) not null unique,
    password varchar(100) not null,
    primary_doctor varchar(50),
    phone_number varchar(10) not null,
    blood_type varchar(4) not null
);

create table if not exists doctor (
    id bigint primary key,
    first_name varchar(20) not null,
    last_name varchar(40) not null,
    email varchar(40) not null unique,
    password varchar(100) not null,
	specialty varchar(30) not null
);

create table if not exists pharmacist (
    id bigint primary key,
    first_name varchar(20) not null,
    last_name varchar(40) not null,
    email varchar(40) not null unique,
    password varchar(100) not null
);

create table if not exists prescription (
    id serial primary key,
    medicine_name varchar(30) not null,
    cc int not null,
    requesting_doctor bigint not null,
    patient_for bigint not null,
    status varchar(13) not null
);

create table if not exists appointment (
    id serial primary key,
    requesting_doctor bigint not null,
    patient_for bigint not null,
    appointment_date date not null,
    appointment_time varchar(5)  not null
);

create table if not exists users (
    id serial  primary key,
    first_name varchar(20) not null,
    last_name varchar(40) not null,
    email varchar(40) not null unique,
    password varchar(100) not null,
	role varchar(13) not null
);

drop table if exists patient CASCADE;
drop table if exists doctor CASCADE;
drop table if exists pharmacist CASCADE;
drop table if exists prescription CASCADE;
drop table if exists appointment CASCADE;
drop table if exists users;
 
 