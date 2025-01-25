
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
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

--CREATE TABLE IF NOT EXISTS target (
--    id INT NOT NULL AUTO_INCREMENT,
--    description VARCHAR(255) NOT NULL,
--    PRIMARY KEY (id)
--);
--CREATE TABLE IF NOT EXISTS labs_target (
--    labs_id INT NOT NULL,
--    target_id INT NOT NULL,
--    PRIMARY KEY (labs_id, target_id),
--    FOREIGN KEY (labs_id) REFERENCES labs(id) ON DELETE CASCADE,
--    FOREIGN KEY (target_id) REFERENCES target(id) ON DELETE CASCADE
--);

INSERT IGNORE INTO role (id, name) VALUES (1, 'SUPER_ADMIN');
INSERT IGNORE INTO role (id, name) VALUES (2, 'ADMIN');
INSERT IGNORE INTO admin (id,role_id,name, email, password, code)
VALUES (1,1,'admin', 'educacciademo@gmail.com', '$2a$10$eoxYXfPwEMb1F/FnX.amJ.MvUCpiorfoGj4jvL3nCST9s9.fMhV/K', NULL);

INSERT IGNORE INTO day_fraction (id, name) VALUES (1, 'morning');
INSERT IGNORE INTO day_fraction (id, name) VALUES (2, 'full day');

INSERT IGNORE INTO status (id, name) VALUES (1, 'Pending');
INSERT IGNORE INTO status (id, name) VALUES (2, 'In Progress');
INSERT IGNORE INTO status (id, name) VALUES (3, 'Completed');
INSERT IGNORE INTO status (id, name) VALUES (4, 'Cancelled');

--INSERT IGNORE INTO target (id, description) VALUES
--(1, 'scuola elementare'),
--(2, 'scuola secondaria di primo grado'),
--(3, 'scuola secondaria di secondo grado');

INSERT IGNORE INTO labs (id, name) VALUES
(1, 'Laboratorio 1 - Storia di Cascina Carla e Bruno Caccia'),
(2, 'Laboratorio 2 - Bruno Caccia'),
(3, 'Laboratorio 3 - Introduzione al tema della mafia'),
(4, 'Laboratorio 4 - I beni confiscati'),
(5, 'Laboratorio 5 - Le mafie in Piemonte'),
(6, 'Laboratorio 6 - Sostenibilità ambientale'),
(7, 'Laboratorio 7 - La mafia attraverso il cinema'),
(8, 'Laboratorio 8 - Il gioco non è un azzardo'),
(9, 'Laboratorio 9 - Chi non gioca vince');


--INSERT IGNORE INTO labs_target (labs_id, target_id) VALUES
--(1, 1), (1, 2), (1, 3),
--(2, 1), (2, 2), (2, 3),
--(3, 1), (3, 2), (3, 3),
--(4, 1), (4, 2), (4, 3),
--(5, 1), (5, 2), (5, 3),
--(6, 1), (6, 2), (6, 3),
--(7, 1), (7, 2), (7, 3),
--(8, 1), (8, 2), (8, 3),
--(9, 1), (9, 2), (9, 3);

