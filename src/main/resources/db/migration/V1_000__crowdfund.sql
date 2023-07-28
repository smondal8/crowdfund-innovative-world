DROP TABLE IF EXISTS ${schemaName}.users CASCADE;
CREATE TABLE ${schemaName}.users
(
   user_id character varying (255) NOT NULL,
   user_name character varying (255) NOT NULL,
   about_me character varying (255) ,
   city character varying (255) NOT NULL,
   phone character varying (10) NOT NULL,
   user_type character varying (255),
   PRIMARY KEY (user_id)
);
DROP SEQUENCE IF EXISTS ${schemaName}.project_id_seq;
CREATE SEQUENCE ${schemaName}.project_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9223372036854775807
   CACHE 1;

DROP TABLE IF EXISTS ${schemaName}.projects CASCADE;
CREATE TABLE ${schemaName}.projects
(
   project_id bigint NOT NULL DEFAULT nextval('project_id_seq'::regclass),
   project_name character varying (255),
   project_description character varying (255),
   project_area character varying (255) NOT NULL,
   project_target bigint NOT NULL,
   PRIMARY KEY (project_id)
);
DROP SEQUENCE IF EXISTS ${schemaName}.users_projects_mapping_seq;
CREATE SEQUENCE ${schemaName}.users_projects_mapping_seq
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9223372036854775807
   CACHE 1;
DROP TABLE IF EXISTS ${schemaName}.users_projects_mapping CASCADE;
CREATE TABLE ${schemaName}.users_projects_mapping
(
   mapping_id bigint NOT NULL DEFAULT nextval('users_projects_mapping_seq'::regclass),
   user_id character varying (255) NOT NULL,
   project_id bigint NOT NULL,
   CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
   CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES projects (project_id)

);
DROP SEQUENCE IF EXISTS ${schemaName}.fundings_id_seq;
CREATE SEQUENCE ${schemaName}.fundings_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9223372036854775807
   CACHE 1;
DROP TABLE IF EXISTS ${schemaName}.fundings CASCADE;
CREATE TABLE ${schemaName}.fundings
(
   fund_id bigint NOT NULL DEFAULT nextval('fundings_id_seq'::regclass),
   amount bigint NOT NULL,
   project_id bigint NOT NULL,
   CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES projects (project_id)

);
DROP SEQUENCE IF EXISTS ${schemaName}.credentials_seq;
CREATE SEQUENCE ${schemaName}.credentials_seq
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9223372036854775807
   CACHE 1;
DROP TABLE IF EXISTS ${schemaName}.credentials CASCADE;
CREATE TABLE ${schemaName}.credentials
(
   cred_id bigint NOT NULL DEFAULT nextval('credentials_seq'::regclass),
   user_id character varying (255),
   encrypted_password character varying (255),
   CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);
