INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO users(username, password) VALUES ('user1','$2a$12$OHoHWzv/mqABR85xMoC.D.cSMfCOmMzn.MXsvqvUwWaF/uRaUdC/y');
INSERT INTO users(username, password) VALUES ('admin','$2a$12$f9cTl0iTx2l6wBMnamthwecBkQaRBxVNzEI4InDX10Np7bDkp0UzO');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- admin with ROLE_ADMIN
