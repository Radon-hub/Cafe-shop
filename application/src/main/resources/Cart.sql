CREATE TABLE carts (
                       id          UUID PRIMARY KEY,
                       customer_id UUID NOT NULL UNIQUE,
                       created_at  TIMESTAMP NOT NULL,
                       updated_at  TIMESTAMP NOT NULL,
                       CONSTRAINT fk_carts_customer
                           FOREIGN KEY (customer_id) REFERENCES users (id)
);