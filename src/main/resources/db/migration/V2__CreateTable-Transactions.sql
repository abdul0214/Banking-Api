CREATE TABLE transactions
(
    id      SERIAL PRIMARY KEY,
    time        TIMESTAMP WITH TIME ZONE,
    amount      NUMERIC,
    details     TEXT,
    reference_no BIGINT,
    source_account_id integer REFERENCES accounts (id),
    target_account_id integer REFERENCES accounts (id)
);