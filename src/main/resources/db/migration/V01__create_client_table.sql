CREATE TABLE client (
    cpf VARCHAR(14) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    registration_date TIMESTAMP NOT NULL
);