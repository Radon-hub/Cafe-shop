-- We remove the unique CONSTRAINT because need more than 1 cart for each user
ALTER TABLE carts DROP CONSTRAINT uq_carts_customer;