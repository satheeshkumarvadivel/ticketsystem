
create table ticketuser (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(120),
	email VARCHAR(120)
);

create table ticket (
	id SERIAL PRIMARY KEY NOT NULL,
	title VARCHAR(240) NOT NULL,
	description VARCHAR(1000),
	type  VARCHAR(60),
	priority VARCHAR(2),
	createdby INT references ticketuser(id),
	assignedto INT references ticketuser(id),
	customer INT references ticketuser(id),
	status VARCHAR(60),
	createdtime BIGSERIAL
);

create table response (
	id SERIAL PRIMARY KEY NOT NULL,
	ticketid INT references ticket(id),
	respondedby INT references ticketuser(id),
	response VARCHAR(1000)
);