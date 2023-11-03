CREATE TABLE Test.Country (
    Id INT PRIMARY KEY,
    Name VARCHAR(225)
);

CREATE TABLE Test.City (
    Id INT PRIMARY KEY,
    Name VARCHAR(225),    
    Country INT,
    CONSTRAINT CountryFK 
        FOREIGN KEY (Country) 
            REFERENCES Test.Country (Id)
);

CREATE TABLE Test.Patient (
    Name VARCHAR(225),
    Lastname VARCHAR(225),
    Photo LONGVARBINARY,
    Phone VARCHAR(14),
    Address VARCHAR(225),
    City INT,
    CONSTRAINT CityFK 
        FOREIGN KEY (City) 
            REFERENCES Test.City (Id)
);

INSERT INTO Test.Country VALUES (1,'Spain'), (2,'France'), (3,'Portugal'), (4,'Germany');

INSERT INTO Test.City VALUES (1,'Madrid',1), (2,'Valencia',1), (3,'Paris',2), (4,'Bordeaux',2), (5,'Lisbon',3), (6,'Porto',3), (7,'Berlin',4), (8,'Frankfurt',4);
