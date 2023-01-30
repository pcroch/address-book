CREATE TABLE qr_code (
                                qr_code_id serial PRIMARY KEY,
                                qr_code_name text UNIQUE,
                                qr_code_image bytea
)
--                                qr_code_id serial PRIMARY KEY,