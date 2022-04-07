CREATE TABLE USER (
    username varchar(20) not null unique,
    password varchar(100) not null,
   	primary key(username)
);


CREATE TABLE USERINFO (
    ID varchar(36) not null,
    name varchar(20) not null,
    email varchar(40) not null,
    phone varchar(10) not null,
   	primary key(ID)
);