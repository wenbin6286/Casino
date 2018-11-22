drop table if exists customer;
create table customer (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(100) not null,
email VARCHAR(100) not null,
created_on date not null,
primary key (id));