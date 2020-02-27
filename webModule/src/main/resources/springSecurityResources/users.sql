drop table if exists user;

 
create table user
(
id   TINYINT not null auto_increment,
 username VARCHAR(100) not null,
 password VARCHAR(100) not null,
 role VARCHAR(36) NOT null,
 enabled  TINYINT not null DEFAULT 1,
 
 constraint user_role_company primary key (id));
 
 

 

 
 
insert into user (username, password, role,enabled) values ('Others', '2020','USER', 1);
 
insert into user (username, password,role, enabled) values ('Issa', '2020','ADMIN', 1);  
 
