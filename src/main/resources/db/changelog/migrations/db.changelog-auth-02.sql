--liquibase formatted sql

--changeset celio:5

INSERT INTO oauth.oauth_user(user_id, user_name, user_password, user_full_name, super_user)
  VALUES (1, 'user', /*1234*/'$2a$10$UtSgFInoXOGAzuEA93Hg7.Z6lPcWUIgaPttERZvLnx3KuKsmAVo8a', 'user principal', false);

--rollback delete from oauth.oauth_user where user_id = 1;

--changeset celio:6

INSERT INTO oauth.oauth_authority(authority_id, authority_name) VALUES (1, 'USER_CREATE');

--rollback delete from oauth.oauth_authority where authority_id = 1;

--changeset celio:7

INSERT INTO oauth.oauth_user_authority(user_id, authority_id) VALUES (1, 1);

--rollback delete from oauth.oauth_user_authority where user_id = 1;
