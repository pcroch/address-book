INSERT INTO address (address_id, street_number,street_name,zipcode,locality,country) VALUES (1, '78' , 'Albert Street', '7800', 'Brussels', 'Belgium' );
INSERT INTO address (address_id, street_number,street_name,zipcode,locality,country, is_private) VALUES (2, '1' , 'Duc Strassen', '4521', 'Luxembourg', 'Luxembourg', false );
INSERT INTO person (person_id, firstname,lastname) VALUES (1, 'Pierre', 'Test' );
INSERT INTO person (person_id, firstname, secondname,lastname) VALUES (2, 'Tom', 'Jerry', 'qwerty' );

INSERT INTO person_address (address_id, person_id) VALUES (1,1 );
INSERT INTO person_address (address_id, person_id) VALUES (2,1 );


