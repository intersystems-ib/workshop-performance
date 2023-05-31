CREATE DATABASE IF NOT EXISTS test;

USE test;

CREATE TABLE patient (
    Id int(8) NOT NULL AUTO_INCREMENT,
    Name varchar(225),
    Lastname varchar(225),
    Photo blob,
    PRIMARY KEY (Id)
);

INSERT INTO patient VALUES (null, 'Juan','Sanz', null), (null, 'Lorena', 'Gil', null);
