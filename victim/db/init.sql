CREATE DATABASE IF NOT EXISTS mydb;

CREATE USER IF NOT EXISTS 'myuser'@'%' IDENTIFIED BY 'mypassword';
GRANT ALL PRIVILEGES ON mydb.* TO 'myuser'@'%';

FLUSH PRIVILEGES;

CREATE TABLE devices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    device INT,
    email VARCHAR(100)
);

INSERT INTO devices (name) VALUES
    ('Smartphone'),
    ('Laptop'),
    ('Headphones'),
    ('Smartwatch'),
    ('Game console');
