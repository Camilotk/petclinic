CREATE TABLE contact (
    id SERIAL PRIMARY KEY,
    client_cpf VARCHAR(11) NOT NULL REFERENCES client(cpf),
    type VARCHAR(255),
    price_amount NUMERIC(19,2),
    price_currency VARCHAR(255),
    CONSTRAINT contact_client_fk FOREIGN KEY (client_cpf) REFERENCES client(cpf)
);