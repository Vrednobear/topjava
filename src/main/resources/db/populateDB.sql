DELETE FROM users;
DELETE FROM user_roles;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name,email,password)
VALUES ('Ton','Vonuchka@yandex.ru','password');

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin'),
('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role,user_id) VALUES ('ROLE_USER',100000);
INSERT INTO user_roles (role,user_id) VALUES ('ROLE_ADMIN',100001);
