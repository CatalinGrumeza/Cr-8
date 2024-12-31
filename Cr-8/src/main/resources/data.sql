
CREATE TABLE IF NOT EXISTS status (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS day_fraction (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS admin (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    code VARCHAR(7)
);
CREATE TABLE IF NOT EXISTS labs (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(100) NOT NULL,
	descrizione VARCHAR(255)  
);
CREATE TABLE IF NOT EXISTS role (
	id INT PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO admin (id,name, email, password, code)
VALUES (1,'admin', 'educacciademo@gmail.com', '1234', NULL);


INSERT IGNORE INTO day_fraction (id, name) VALUES (1, 'morning');
INSERT IGNORE INTO day_fraction (id, name) VALUES (2, 'evening');
INSERT IGNORE INTO day_fraction (id, name) VALUES (3, 'full day');

INSERT IGNORE INTO status (id, name) VALUES (1, 'Pending');
INSERT IGNORE INTO status (id, name) VALUES (2, 'In Progress');
INSERT IGNORE INTO status (id, name) VALUES (3, 'Completed');
INSERT IGNORE INTO status (id, name) VALUES (4, 'Cancelled');
INSERT IGNORE INTO labs ( name, descrizione) VALUES("lab_1",null);
INSERT IGNORE INTO role (id, name) VALUES (1, 'Super admin');
INSERT IGNORE INTO role (id, name) VALUES (2, 'Admin');

