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