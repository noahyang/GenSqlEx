DROP TABLE IF EXISTS base_book;
CREATE TABLE base_book(
id INTEGER PRIMARY KEY,
name TEXT,
author TEXT,
price FLOAT,
time TEXT,
page INTEGER,
size FLOAT,
desc TEXT
);

DROP TABLE IF EXISTS base_test;
CREATE TABLE base_test(
id INTEGER PRIMARY KEY,
name TEXT,
author TEXT,
price FLOAT,
desc TEXT
);