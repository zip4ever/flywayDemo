create table LEGAL_HOLIDAY (
  id serial,
	date date not null,
	description_nl varchar(255),
	description_fr varchar(255),
	description_de varchar(255),
	description_en varchar(255)
);

ALTER TABLE LEGAL_HOLIDAY ADD CONSTRAINT HolidayUniqueConstraint UNIQUE(date);