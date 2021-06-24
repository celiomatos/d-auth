--liquibase formatted sql

--changeset celio:1
CREATE SCHEMA oauth;

--changeset celio:2

CREATE TABLE oauth.oauth_user
(
  user_id serial PRIMARY KEY,
  user_name character varying(256) NOT NULL UNIQUE,
  user_password character varying(256) NOT NULL,
  user_full_name character varying(256) NOT NULL,
  super_user boolean NOT NULL default false
);

--rollback drop table oauth.oauth_user;

--changeset celio:3

CREATE TABLE oauth.oauth_authority
(
  authority_id serial PRIMARY KEY,
  authority_name character varying(256) NOT NULL UNIQUE
);

--rollback drop table oauth.oauth_authority;


--changeset celio:4

CREATE TABLE oauth.oauth_user_authority
(
  user_id int NOT NULL REFERENCES oauth.oauth_user (user_id),
  authority_id int NOT NULL REFERENCES oauth.oauth_authority (authority_id),
  UNIQUE(user_id, authority_id)
);

--rollback drop table oauth.oauth_user_authority;
