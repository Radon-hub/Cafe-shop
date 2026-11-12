CREATE SEQUENCE res_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE reserved
(
    id              BIGINT                          NOT NULL,
    product_id      BIGINT                          NOT NULL,
    order_id        UUID                            NOT NULL,
    count           INTEGER                         NOT NULL,
    expires_at      TIMESTAMP WITHOUT TIME ZONE     NOT NULL,
    status          VARCHAR(20)                     NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE     NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE     NOT NULL,
    CONSTRAINT pk_reserved PRIMARY KEY (id),
    CONSTRAINT fk_reserved_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_reserved_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE INDEX idx_reserved_product ON reserved (product_id);
CREATE INDEX idx_reserved_order ON reserved (order_id);
