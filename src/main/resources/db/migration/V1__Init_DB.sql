create sequence hibernate_sequence start 1 increment 1;

create table contact (
  id int8 not null,
  company varchar(255) not null,
  email varchar(255) not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  phone varchar(32) not null,
  primary key (id)
);