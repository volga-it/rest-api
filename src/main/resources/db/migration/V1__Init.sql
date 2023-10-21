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
    role INT NOT NULL DEFAULT 1,
    FOREIGN KEY (role) REFERENCES account_role (role_id)
);

INSERT INTO accounts (username, password, banned, role) VALUES ('YCb4Afsmgq0o3UpAei33eOs6lSutJJwb', 'qtaZcNBxuVGDBJUv2awbgOOH035tFICZ', false, 2);

CREATE TABLE transports (
    transport_id BIGSERIAL PRIMARY KEY,
    rented BOOLEAN NOT NULL DEFAULT false,
    ttype INT NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    description TEXT,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    price REAL,
    FOREIGN KEY (ttype) REFERENCES transport_type (type_id)
);

CREATE TABLE rents (
    rent_id BIGSERIAL PRIMARY KEY,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    radius REAL NOT NULL,
    rtype INT NOT NULL,
    FOREIGN KEY (rtype) REFERENCES transport_type (type_id)
);