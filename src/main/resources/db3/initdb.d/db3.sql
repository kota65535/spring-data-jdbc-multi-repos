DROP DATABASE IF EXISTS db3;
GO
CREATE DATABASE db3;
GO

CREATE TABLE users
(
    id int PRIMARY KEY IDENTITY (1,1),
    name     varchar(255)
);
GO

INSERT INTO users (name)
VALUES ('spam');
INSERT INTO users (name)
VALUES ('ham');
GO
