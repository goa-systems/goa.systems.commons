CREATE TABLE settings (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "key" TEXT,
    value TEXT
);
--
INSERT INTO settings (id, "key", value) VALUES(1, 'databaseversion', '0.0.1');