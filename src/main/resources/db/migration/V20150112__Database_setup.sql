CREATE TABLE employees
(
  id serial,
  emp_hours_per_day numeric(19,2),
  emp_last_name character varying(255),
  emp_name character varying(255),
  emp_vacation_rights numeric(19,2),
  CONSTRAINT employees_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employees
  OWNER TO postgres;


CREATE TABLE registrations
(
  id SERIAL,
  reg_date date,
  reg_type integer,
  reg_time time,
  CONSTRAINT registrations_pkey PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE registrations
OWNER TO postgres;

CREATE TABLE employees_registrations
(
  employees_id bigint NOT NULL,
  registrations_id bigint NOT NULL,
  CONSTRAINT fk_employee_id FOREIGN KEY (employees_id)
  REFERENCES employees (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_registrations_id FOREIGN KEY (registrations_id)
  REFERENCES registrations (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_registrations_id UNIQUE (registrations_id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE employees_registrations
OWNER TO postgres;