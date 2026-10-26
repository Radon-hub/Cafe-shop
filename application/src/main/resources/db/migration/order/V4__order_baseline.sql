CREATE TABLE orders
(
    id              UUID                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    idempotency_key VARCHAR(255)                NOT NULL,
    order_number    VARCHAR(255)                NOT NULL,
    order_status    SMALLINT                    NOT NULL,
    payment_id      VARCHAR(255)                NOT NULL,
    total_price     NUMERIC(38, 2)              NOT NULL,
    user_id         UUID                        NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id),
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE order_items
(
    id         UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    price      NUMERIC(38, 2)              NOT NULL,
    quantity   INTEGER                     NOT NULL,
    order_id   UUID                        NOT NULL,
    product_id BIGINT                      NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products (id)
);