CREATE DATABASE IF NOT EXISTS cryptmyfile;

USE cryptmyfile;

CREATE TABLE IF NOT EXISTS User (
    ID INT NOT NULL AUTO_INCREMENT,
    Mail VARCHAR(100) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Salt VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE (Mail)
);
