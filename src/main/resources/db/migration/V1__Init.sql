CREATE TYPE account_role AS ENUM('User', 'Admin');

CREATE TABLE accounts (
    account_id BIGSERIAL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    banned BOOLEAN NOT NULL DEFAULT false,
    role account_role NOT NULL
);

INSERT INTO accounts (username, password, banned, role) VALUES ('YCb4Afsmgq0o3UpAei33eOs6lSutJJwb', 'qtaZcNBxuVGDBJUv2awbgOOH035tFICZ', false, 'Admin');

CREATE TYPE transport_type AS ENUM ('Car', 'Bike', 'Scooter');

CREATE TABLE transports (
    transport_id BIGSERIAL,
    rented BOOLEAN NOT NULL DEFAULT false,
    ttype transport_type NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    description TEXT,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    price REAL
);

CREATE TYPE rent_transport_type AS ENUM ('Car', 'Bike', 'Scooter', 'All');

CREATE TABLE rents (
    rent_id BIGSERIAL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    radius REAL NOT NULL,
    rtype rent_transport_type NOT NULL
);