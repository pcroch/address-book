CREATE TABLE person_address_per_code (
                                person_address_per_code_id serial PRIMARY KEY,
                                qr_code_id serial,
                                person_address_id serial,
                                CONSTRAINT fk_person_address FOREIGN KEY(person_address_id) REFERENCES person_address(person_address_id),
                                CONSTRAINT fk_qr_code FOREIGN KEY(qr_code_id) REFERENCES qr_code(qr_code_id)
);
