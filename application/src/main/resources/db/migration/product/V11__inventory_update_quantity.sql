ALTER TABLE inventory
    ADD COLUMN physical_quantity INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN reserved_quantity INTEGER NOT NULL DEFAULT 0;

-- Preserve existing inventory counts
UPDATE inventory
    SET physical_quantity = count;

ALTER TABLE inventory
    DROP COLUMN count;

ALTER TABLE inventory
    ADD CONSTRAINT chk_inventory_quantities
        CHECK(
            physical_quantity >= 0
                AND reserved_quantity >= 0
                AND reserved_quantity <= physical_quantity
             );