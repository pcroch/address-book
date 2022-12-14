CREATE TABLE person_address (
                                person_address_id serial PRIMARY KEY,
                                address_id serial,
                                person_id serial,
                                CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(person_id),
                                CONSTRAINT fk_address FOREIGN KEY(address_id) REFERENCES address(address_id)
);
