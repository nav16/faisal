CREATE TYPE user_type AS ENUM (
  'NORMAL',
  'ADMIN'
  );

CREATE TABLE users (
                     id BIGINT NOT NULL PRIMARY KEY,
                     name character varying,
                     email character varying DEFAULT ''::character varying NOT NULL,
                     user_type user_type,
                     created_at timestamp without time zone,
                     updated_at timestamp without time zone
);

CREATE SEQUENCE users_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;
ALTER TABLE ONLY users
  ALTER COLUMN id SET DEFAULT nextval('users_id_seq' :: REGCLASS);
