CREATE TABLE address (
                         address_id serial PRIMARY KEY,
                         street_number VARCHAR ( 50 ) NOT NULL,
                         box_number  VARCHAR( 50 ),
                         street_name  VARCHAR( 50 ) NOT NULL,
                         zipcode VARCHAR ( 50 ) NOT NULL,
                         locality VARCHAR ( 50 ) NOT NULL,
                         country VARCHAR ( 50 ) NOT NULL,
                         is_private BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE person (
                        person_id serial PRIMARY KEY,
                        firstname VARCHAR ( 50 ) NOT NULL,
                        secondname VARCHAR ( 50 ),
                        lastname VARCHAR ( 50 )
);

CREATE TABLE person_address (
                                address_id serial,
                                person_id serial,
                                CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(person_id),
                                CONSTRAINT fk_address FOREIGN KEY(address_id) REFERENCES address(address_id)
)
