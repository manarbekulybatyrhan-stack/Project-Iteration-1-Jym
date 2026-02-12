CREATE TABLE IF NOT EXISTS memberships (
                                           id SERIAL PRIMARY KEY,
                                           type VARCHAR(50),
    duration_months INT,
    price DECIMAL
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
    type VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
    );

ALTER TABLE memberships
    ADD COLUMN IF NOT EXISTS category VARCHAR(50);

ALTER TABLE training_sessions
    ADD COLUMN IF NOT EXISTS category VARCHAR(50);

UPDATE memberships
SET category = 'BASIC'
WHERE category IS NULL;

UPDATE training_sessions
SET category = 'CARDIO'
WHERE category IS NULL;

ALTER TABLE memberships
    ALTER COLUMN category SET NOT NULL;

ALTER TABLE training_sessions
    ALTER COLUMN category SET NOT NULL;




