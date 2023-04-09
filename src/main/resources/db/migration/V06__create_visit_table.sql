CREATE TABLE visit (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    visit_date DATE,
    description VARCHAR(255) NOT NULL,
    pet_id BIGINT REFERENCES pet(id) ON DELETE CASCADE,
    price_amount NUMERIC(19,2) NOT NULL,
    price_currency VARCHAR(3) DEFAULT 'BRL'
);