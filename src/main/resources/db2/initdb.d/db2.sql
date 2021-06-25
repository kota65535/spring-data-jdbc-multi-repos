DROP DATABASE IF EXISTS db2;
CREATE DATABASE db2
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE db2;

CREATE TABLE users
(
    id       int PRIMARY KEY AUTO_INCREMENT,
    name     varchar(255)
);

INSERT INTO users (name) VALUES ('hoge');
INSERT INTO users (name) VALUES ('piyo');
