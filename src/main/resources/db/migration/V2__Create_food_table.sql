create table if not exists food
(
	id int auto_increment
		primary key,
	account_id varchar(100) null,
	name varchar(100) null,
	token char(36) null,
	gmt_create bigint null,
	gmt_modified bigint null,
	boi varchar(256) null
);