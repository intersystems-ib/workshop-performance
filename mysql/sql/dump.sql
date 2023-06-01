CREATE DATABASE IF NOT EXISTS test;

USE test;

CREATE TABLE country (
    Id int(8) NOT NULL,
    Name varchar(225),
    PRIMARY KEY (Id)
) ENGINE=INNODB;

CREATE TABLE city (
    Id int(8) NOT NULL,
    Name varchar(225),
    Country int(8),
    PRIMARY KEY (Id),
    FOREIGN KEY (Country)
        REFERENCES country(id)
        ON DELETE NO ACTION
) ENGINE=INNODB;

CREATE TABLE patient (
    Id int(8) NOT NULL AUTO_INCREMENT,
    Name varchar(225),
    Lastname varchar(225),
    Photo blob,
    Phone varchar (14),
    Address varchar (225),
    City int(8),
    PRIMARY KEY (Id),
    FOREIGN KEY (City)
        REFERENCES city(Id)
        ON DELETE CASCADE
) ENGINE=INNODB;

INSERT INTO country VALUES (1,'Spain'), (2,'France'), (3,'Portugal'), (4,'Germany');

INSERT INTO city VALUES (1,'Madrid',1), (2,'Valencia',1), (3,'Paris',2), (4,'Bordeaux',2), (5,'Lisbon',3), (6,'Porto',3), (7,'Berlin',4), (8,'Frankfurt',4);
