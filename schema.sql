CREATE TABLE memberships (
                             id SERIAL PRIMARY KEY,
                             type VARCHAR(50),
                             duration_months INT,
                             price DECIMAL
);

CREATE TABLE trainers (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100),
                          specialization VARCHAR(100),
                          email VARCHAR(100),
                          phone VARCHAR(20)
);

CREATE TABLE members (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100),
                         email VARCHAR(100),
                         phone VARCHAR(20),
                         membership_id INT REFERENCES memberships(id),
                         join_date DATE,
                         expiry_date DATE
);

CREATE TABLE training_sessions (
                                   id SERIAL PRIMARY KEY,
                                   member_id INT REFERENCES members(id),
                                   trainer_id INT REFERENCES trainers(id),
                                   session_date TIMESTAMP,
                                   duration_minutes INT,
                                   type VARCHAR(50)
);

