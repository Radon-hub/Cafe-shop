-- Adding new status column to carts
ALTER TABLE carts
    ADD COLUMN status VARCHAR(20);

-- Set all carts with active status
UPDATE carts SET status = 'ACTIVE';

-- Make the status column non-nullable
ALTER TABLE carts
    ALTER COLUMN status SET NOT NULL;

