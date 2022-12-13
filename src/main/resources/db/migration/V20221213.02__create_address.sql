CREATE TABLE IF not exists ADDRESS (
                         address_id serial PRIMARY KEY,
                         street_number VARCHAR ( 50 ) NOT NULL,
                         box_number  VARCHAR( 50 ),
                         street_name  VARCHAR( 50 ) NOT NULL,
                         zipcode VARCHAR ( 50 ) NOT NULL,
                         locality VARCHAR ( 50 ) NOT NULL,
                         country VARCHAR ( 50 ) NOT NULL,
                         is_private BOOLEAN NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE ADDRESS IS 'contains the address';

