CREATE OR REPLACE FUNCTION public.trgf_uuid_generator()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
begin
	NEW.id := uuid_in(md5(random()::text || now()::text)::cstring);
	return NEW;
end;
$BODY$;

ALTER FUNCTION public.trgf_uuid_generator()
    OWNER TO postgres;
	
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public."user"
(
    id uuid NOT NULL,
    first_name character varying COLLATE pg_catalog."default",
    last_name character varying COLLATE pg_catalog."default",
	email character varying COLLATE pg_catalog."default",
    username character varying COLLATE pg_catalog."default",
    password character varying COLLATE pg_catalog."default",
    account_not_expired boolean,
    account_not_locked boolean,
    credentials_not_expired boolean,
    enabled boolean,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;

CREATE OR REPLACE TRIGGER trg_user_uuid
    BEFORE INSERT
    ON public."user"
    FOR EACH ROW
    EXECUTE FUNCTION public.trgf_uuid_generator();
	

CREATE UNIQUE INDEX idx_user_username
    ON public."user" USING btree
    (username)
    WITH (deduplicate_items=True);
	
------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.role_id_seq
    OWNED BY public.role.id;

ALTER SEQUENCE public.role_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.role
(
    id integer NOT NULL DEFAULT nextval('role_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.role
    OWNER to postgres;
	
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id uuid NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_role
    OWNER to postgres;
	
------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS public.teaching_subject_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.teaching_subject_id_seq
    OWNED BY public.teaching_subject.id;

ALTER SEQUENCE public.teaching_subject_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.teaching_subject
(
    id integer NOT NULL DEFAULT nextval('teaching_subject_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default",
    CONSTRAINT teaching_subject_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.teaching_subject
    OWNER to postgres;
	
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.user_teaching_subject
(
    user_id uuid NOT NULL,
    teaching_subject_id integer NOT NULL,
    CONSTRAINT user_teaching_subject_pkey PRIMARY KEY (user_id, teaching_subject_id),
    CONSTRAINT fk_teaching_subject_user_teaching_subject FOREIGN KEY (teaching_subject_id)
        REFERENCES public.teaching_subject (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_teaching_subject_user_user FOREIGN KEY (user_id)
        REFERENCES public.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_teaching_subject
    OWNER to postgres;
	
------------------------------------------------------------------------------

INSERT INTO public.role(
	name)
	VALUES ('TUTOR');
	
INSERT INTO public.role(
	name)
	VALUES ('STUDENT');
	
------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS public.availability_period_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.availability_period_id_seq
    OWNED BY public.availability_period.id;

ALTER SEQUENCE public.availability_period_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.availability_period
(
    id bigint NOT NULL DEFAULT nextval('availability_period_id_seq'::regclass),
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT availability_period_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.availability_period
    OWNER to postgres;

------------------------------------------------------------------------------