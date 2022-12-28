CREATE TABLE qr_code (
                                person_address_id serial,
                                qr_code_name text UNIQUE,
                                qr_code_image bytea,
                                PRIMARY KEY (person_address_id),
                                CONSTRAINT fk_person_address FOREIGN KEY(person_address_id) REFERENCES person_address(person_address_id)

)
--                                qr_code_id serial PRIMARY KEY,