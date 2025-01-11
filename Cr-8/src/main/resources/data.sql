
CREATE TABLE IF NOT EXISTS status (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS day_fraction (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS role (
	id INT PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS admin (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    code VARCHAR(7),
    role_id INT,
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS labs (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(100) NOT NULL,
	descrizione VARCHAR(255)  
);

INSERT IGNORE INTO role (id, name) VALUES (1, 'SUPER_ADMIN');
INSERT IGNORE INTO role (id, name) VALUES (2, 'ADMIN');
INSERT IGNORE INTO admin (id,role_id,name, email, password, code)
VALUES (1,1,'admin', 'educacciademo@gmail.com', '$2a$10$eoxYXfPwEMb1F/FnX.amJ.MvUCpiorfoGj4jvL3nCST9s9.fMhV/K', NULL);
INSERT IGNORE INTO labs (id,name, descrizione) VALUES(1,"lab_1",null);


INSERT IGNORE INTO day_fraction (id, name) VALUES (1, 'morning');
INSERT IGNORE INTO day_fraction (id, name) VALUES (2, 'evening');
INSERT IGNORE INTO day_fraction (id, name) VALUES (3, 'full day');

INSERT IGNORE INTO status (id, name) VALUES (1, 'Pending');
INSERT IGNORE INTO status (id, name) VALUES (2, 'In Progress');
INSERT IGNORE INTO status (id, name) VALUES (3, 'Completed');
INSERT IGNORE INTO status (id, name) VALUES (4, 'Cancelled');

