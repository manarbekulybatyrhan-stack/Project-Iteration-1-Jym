-- CREATE DATABASE gymdb;
-- \c gymdb;

CREATE TABLE memberships (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    duration_months INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE members (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    membership_id INT NOT NULL,
    join_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    FOREIGN KEY (membership_id) REFERENCES memberships(id) ON DELETE CASCADE
);

CREATE TABLE trainers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE training_sessions (
    id SERIAL PRIMARY KEY,
    member_id INT NOT NULL,
    trainer_id INT NOT NULL,
    session_date TIMESTAMP NOT NULL,
    duration_minutes INT NOT NULL,
    type VARCHAR(50),
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE CASCADE
);