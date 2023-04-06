CREATE TABLE pet (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    client_cpf VARCHAR(14) REFERENCES client(cpf),
    birth_date DATE NOT NULL,
    race_id BIGINT REFERENCES race(id)
);