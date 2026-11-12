-- This index created because every 30-second reservation
-- and orders will be searched for expired items.
CREATE INDEX idx_reservation_status_expires_at ON reserved(status,expires_at)