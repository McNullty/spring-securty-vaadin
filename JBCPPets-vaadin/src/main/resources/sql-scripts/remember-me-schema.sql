create table persistent_logins (
	username varchar_ignorecase(50) not null, 
	series varchar(64) primary key,
	token varchar(64) not null, 
	last_used timestamp not null);