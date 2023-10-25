DROP DATABASE IF EXISTS db1;
CREATE DATABASE db1
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_bin;

USE db1;

CREATE TABLE users
(
    id       int PRIMARY KEY AUTO_INCREMENT,
    name     varchar(255)
);

INSERT INTO users (name) VALUES ('foo');
INSERT INTO users (name) VALUES ('bar');
