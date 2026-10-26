CREATE SEQUENCE authorities_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE roles_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE users
(
    id         UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    first_name VARCHAR(255)                NOT NULL,
    last_name  VARCHAR(255)                NOT NULL,
    user_name  VARCHAR(255)                NOT NULL,
    password   VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_user_name UNIQUE (user_name)
);

CREATE TABLE roles
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    role       VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uq_roles_role UNIQUE (role)
);

CREATE TABLE authorities
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    authority  VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_authorities PRIMARY KEY (id),
    CONSTRAINT uq_authorities_authority UNIQUE (authority)
);

CREATE TABLE user_roles
(
    user_id UUID   NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE role_authorities
(
    role_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    CONSTRAINT pk_role_authorities PRIMARY KEY (role_id, authority_id),
    CONSTRAINT fk_role_authorities_role FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_role_authorities_authority FOREIGN KEY (authority_id) REFERENCES authorities (id)
);