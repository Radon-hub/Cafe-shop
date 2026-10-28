ALTER TABLE orders
    ADD CONSTRAINT uq_orders_idempotency_key UNIQUE (user_id,idempotency_key),
    ADD CONSTRAINT uq_orders_order_number UNIQUE (order_number);