CREATE TABLE IF NOT EXISTS public.contact
(
  id bigint NOT NULL,
  company character varying(255),
  email character varying(255),
  first_name character varying(255),
  last_name character varying(255),
  phone character varying(255),
  CONSTRAINT contact_pkey PRIMARY KEY (id)
);