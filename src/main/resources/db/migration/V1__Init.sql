CREATE TABLE account_role (
    role_id INT PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL
);

INSERT INTO account_role (role_id, title) VALUES
        (1, 'ROLE_USER'),
        (2, 'ROLE_ADMIN');

CREATE TABLE transport_type (
    type_id INT PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL
);

INSERT INTO transport_type VALUES
        (1, 'Car'),
        (2, 'Bike'),
        (3, 'Scooter'),
        (4, 'All');

CREATE TABLE rent_type (
    type_id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

INSERT INTO rent_type VALUES
        (1, 'Minutes'),
        (2, 'Days');

CREATE TABLE banned_tokens (
    token_id UUID PRIMARY KEY NOT NULL,
    token_base64_payload VARCHAR(1000) UNIQUE NOT NULL,
    time TIMESTAMP NOT NULL
);

CREATE TABLE accounts (
    account_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    banned BOOLEAN NOT NULL DEFAULT false,
    balance DECIMAL DEFAULT 0,
    role INT NOT NULL DEFAULT 1,
    FOREIGN KEY (role) REFERENCES account_role (role_id)
);

INSERT INTO accounts (username, password, banned, role) VALUES ('YCb4Afsmgq0o3UpAei33eOs6lSutJJwb', 'qtaZcNBxuVGDBJUv2awbgOOH035tFICZ', false, 2);

CREATE TABLE transports (
    transport_id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    rented BOOLEAN NOT NULL DEFAULT false,
    ttype INT NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    description TEXT,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    price_minute REAL,
    price_day REAL,
    FOREIGN KEY (owner_id) REFERENCES accounts (account_id),
    FOREIGN KEY (ttype) REFERENCES transport_type (type_id)
);

INSERT INTO transports (owner_id, rented, ttype, model, color, identifier, description, latitude, longitude, price_minute, price_day)
VALUES (1, false, 1, 'not model', 'not color', 'not know', 'not description', 0.0, 0.0, 0.0, 1.0);

CREATE TABLE rents (
    rent_id UUID PRIMARY KEY,
    transport_id BIGINT NOT NULL,
    renter_id BIGINT NOT NULL,
    time_open TIMESTAMP NOT NULL,
    time_close TIMESTAMP,
    rent_type INT NOT NULL,
    FOREIGN KEY (transport_id) REFERENCES transports (transport_id),
    FOREIGN KEY (renter_id) REFERENCES accounts (account_id),
    FOREIGN KEY (rent_type) REFERENCES rent_type (type_id)
);

CREATE TABLE payments (
    payment_id UUID PRIMARY KEY,
    done BOOLEAN DEFAULT false,
    payer BIGINT NOT NULL,
    amount DECIMAL NOT NULL,
    rent UUID NOT NULL UNIQUE,
    FOREIGN KEY (payer) REFERENCES accounts (account_id),
    FOREIGN KEY (rent) REFERENCES rents (rent_id)
);