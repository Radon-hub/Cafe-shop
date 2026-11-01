-- Make order status to varchar
ALTER TABLE orders
    ALTER COLUMN order_status TYPE VARCHAR(50);

-- Adding more extra data to order items to be more explicit
ALTER TABLE order_items
    ADD COLUMN total_price  NUMERIC(38, 2),
    ADD COLUMN product_name VARCHAR(255),
    ADD COLUMN product_desc TEXT;

-- Update the empty column with product data
UPDATE order_items oi
SET
    total_price = oi.price * oi.quantity,
    product_name = p.name,
    product_desc = p.description
FROM products p
WHERE oi.product_id = p.id;

-- Make the added columns non-nullable
ALTER TABLE order_items
    ALTER COLUMN total_price SET NOT NULL,
    ALTER COLUMN product_name SET NOT NULL;