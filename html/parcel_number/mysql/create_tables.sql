-- create db
create database partialnumber_db;

-- create tables
create table email_addresses (
	email_id int unsigned not null auto_increment,
	email_address varchar(100) not null,
	primary key (email_id),
	key email (email_address)
) engine=innodb
	default charset=utf8
	default collate=utf8_unicode_ci;

create table partial_numbers (
	pnumber_id int unsigned not null auto_increment,
	partial_number varchar(50) not null,
	primary key (pnumber_id),
	key pnumber (partial_number)
) engine=innodb
	default charset=utf8
	default collate=utf8_unicode_ci;


create table email_pnumber_connections (
	connection_id bigint unsigned not null auto_increment,
	email_id int unsigned not null,
	pnumber_id int unsigned not null,
	primary key (connection_id),
	foreign key (email_id) references email_addresses(email_id),
	foreign key (pnumber_id) references partial_numbers(pnumber_id)
) engine=innodb
	default charset=utf8
	default collate=utf8_unicode_ci;


-- use db if already exists
use partialnumber_db;

-- see table preferences if already exists
describe email_addresses;
describe partial_numbers;
describe email_pnumber_connections;
