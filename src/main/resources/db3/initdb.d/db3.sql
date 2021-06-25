DROP DATABASE IF EXISTS db3;
CREATE DATABASE db3 ENCODING 'UTF-8';

\c db3;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     varchar(255)
);

INSERT INTO users (name) VALUES ('hoge');
INSERT INTO users (name) VALUES ('piyo');
