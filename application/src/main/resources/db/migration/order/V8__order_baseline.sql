-- Adding the user address column
ALTER TABLE orders
    ADD COLUMN address VARCHAR(255)