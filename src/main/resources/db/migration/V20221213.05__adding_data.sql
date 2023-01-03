insert into person (person_id, firstname,lastname) values (1, 'Pierre', 'Test' );
insert into person (person_id, firstname, secondname,lastname) values (2, 'Tom', 'Jerry', 'qwerty' );
insert into person (person_id, firstname, secondname,lastname) values (3, 'Jane', null, 'Doe' );

insert into address (address_id, street_number,street_name,zipcode,locality,country) values (1, '16' , 'Rue Brederode', '1000', 'Bruxelles', 'Belgium' );
insert into address (address_id, street_number,street_name,zipcode,locality,country, is_private) values (2, '1' , 'Bd Franklin Delano Roosevelt', '1356', 'Luxembourg', 'Luxembourg', false );
insert into address (address_id, street_number,street_name,zipcode,locality,country, is_private) values (3, '10' , 'Av. de la Paix', '1202', 'Genève', 'Suisse', false );


--insert into person_address (person_address_id, address_id, person_id) values (1, 1,1 );
--insert into person_address (person_address_id, address_id, person_id) values (2, 2,2 );
--insert into person_address (person_address_id, address_id, person_id) values (3, 3,1 );
--insert into person_address (person_address_id, address_id, person_id) values (4, 1,2 );
