CREATE TABLE IF NOT EXISTS memberships (
                                           id SERIAL PRIMARY KEY,
                                           type VARCHAR(50),
    duration_months INT,
    price DECIMAL,
    category VARCHAR(50) NOT NULL DEFAULT 'BASIC'
    );

CREATE TABLE IF NOT EXISTS trainers (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(100),
    specialization VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS members (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    membership_id INT REFERENCES memberships(id),
    join_date DATE,
    expiry_date DATE
    );

CREATE TABLE IF NOT EXISTS training_sessions (
                                                 id SERIAL PRIMARY KEY,
                                                 member_id INT REFERENCES members(id),
    trainer_id INT REFERENCES trainers(id),
    session_date TIMESTAMP,
    duration_minutes INT,
    type VARCHAR(50),
    category VARCHAR(50) NOT NULL DEFAULT 'CARDIO'
    );

CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE
    );

INSERT INTO users (username, password, role)
VALUES ('admin', '0000', 'ADMIN')
    ON CONFLICT (username) DO NOTHING;



