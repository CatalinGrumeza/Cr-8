
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
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    scope VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS target (
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS labs_target (
    labs_id INT NOT NULL,
    target_id INT NOT NULL,
    PRIMARY KEY (labs_id, target_id),
    FOREIGN KEY (labs_id) REFERENCES labs(id) ON DELETE CASCADE,
    FOREIGN KEY (target_id) REFERENCES target(id) ON DELETE CASCADE
);

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

INSERT IGNORE INTO target (id, description) VALUES
(1, 'scuola elementare'),
(2, 'scuola secondaria di primo grado'),
(3, 'scuola secondaria di secondo grado');

INSERT IGNORE INTO labs (id, name, description, scope) VALUES
(1, 'Storia della Cascina Carla e Bruno Caccia', 'Sensibilizzare i partecipanti sulla lotta alla mafia e mostrare l\'importanza del riutilizzo dei beni confiscati per il bene comune.', 'Visita guidata della cascina, con spiegazioni dettagliate sulla storia del bene confiscato.'),
(2, 'Bruno Caccia - Magistrato e Uomo', 'Far conoscere la figura di Bruno Caccia come esempio di coraggio e dedizione nella lotta alla mafia, approfondendo i suoi ideali e il significato del suo sacrificio.', 'Proiezione di un documentario di circa 50 minuti e discussione sulle sfide della magistratura contro la criminalità organizzata.'),
(3, 'I Beni Confiscati', 'Mostrare il valore del riutilizzo sociale dei beni confiscati come strumento per costruire giustizia e inclusione.', 'Introduzione teorica su cosa sono i beni confiscati e come vengono assegnati.'),
(4, 'Le mafie & il cibo', 'Riflettere sul ruolo di ciascuno di noi come consumatore e sulla responsabilità delle nostre scelte d\'acquisto nel contrasto a questi fenomeni.', 'Riflessione sul concetto di filiera: come il cibo passa dal campo alle nostre tavole? Quali trasformazioni subisce? Chi sono gli attori coinvolti?'),
(5, 'Miele & Api', 'Conoscenza della società delle api, confronto con la nostra società. Cosa è simile? Cosa c\'è di diverso? Cosa possiamo apprendere?', 'Laboratorio di apicoltura base (la società delle api, l\'attività dell\'apicoltore, i prodotti dell\'alveare), di conoscenza dei tipi di miele, piccola degustazione dei mieli della cascina.'),
(6, 'RICICLIAMO attività artistiche e manuali', 'Riflessione sulla riduzione dei consumi e il riuso.', 'Creazione di oggetti e opere d’arte utilizzando materiali riciclati, esplorando il riuso creativo.'),
(7, 'La mafia attraverso il cinema percezioni culturali', 'Analizzare e verificare il rapporto tra rappresentazione della criminalità mafiosa attraverso il cinema e realtà effettiva.', 'Proiezione e analisi di spezzoni di film, seguita da una discussione guidata.'),
(8, 'Il gioco non è un azzardo', 'Esplorare e riflettere sul mondo del gioco.', 'Giochi e attività educative per scoprire l’importanza di un approccio sano e consapevole al gioco.'),
(9, 'Chi non gioca vince', 'Individuazione e conoscenza dei rischi dovuti a un abuso e una dipendenza da gioco d\'azzardo e collegamenti con la mafia e il gioco d\'azzardo illegale.', 'Workshop interattivi e momenti di riflessione sui rischi del gioco d’azzardo, con possibili collegamenti con il fenomeno mafioso.'),
(10, 'Regole e Legalità per i più Piccoli', 'Educare i più piccoli al rispetto delle regole, alla responsabilità personale e al vivere in comunità.', 'Giochi educativi per riflettere sul significato delle regole e dialoghi guidati per esplorare il valore della legalità nella vita quotidiana.');


INSERT IGNORE INTO labs_target (labs_id, target_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2), (2, 3),
(3, 1), (3, 2), (3, 3),
(4, 1), (4, 2), (4, 3),
(5, 1), (5, 2), (5, 3),
(6, 1), (6, 2), (6, 3),
(7, 1), (7, 2), (7, 3),
(8, 1), (8, 2), (8, 3),
(9, 1), (9, 2), (9, 3);

