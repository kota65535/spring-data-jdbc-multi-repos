DROP DATABASE IF EXISTS db2;
CREATE DATABASE db2 ENCODING 'UTF-8';

\c db2;

CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    name     varchar(255)
);

INSERT INTO users (name)
VALUES ('hoge');
INSERT INTO users (name)
VALUES ('piyo');
