DROP TABLE IF EXISTS users;
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR (255) NOT NULL,
    password VARCHAR (256) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles(
    users_id BIGINT REFERENCES users(id),
    name VARCHAR (255) NOT NULL,
    PRIMARY KEY(users_id, name)
);


INSERT INTO users(id, email, password, created_at, updated_at)
VALUES (1, 'poxos@email.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', current_timestamp, current_timestamp)
INSERT INTO roles(users_id, name) 
VALUES (1, 'USER');

INSERT INTO users(id, email, password, created_at, updated_at)
VALUES (2, 'petros@email.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', current_timestamp, current_timestamp)
INSERT INTO roles(users_id, name) 
VALUES (2, 'USER');
