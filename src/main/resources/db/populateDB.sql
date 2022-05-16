DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password,registered)
VALUES ('User', 'user@yandex.ru', 'password','2020-01-30 09:00:00'),
('Admin', 'admin@gmail.com', 'admin','2019-02-10 16:55:00');
-- ('Guest', 'guest@gmail.com', 'guest');

-- INSERT INTO users(name,email,password)
-- VALUES ('Ton','Vonuchka@yandex.ru','password');

INSERT INTO user_roles (role,user_id) VALUES ('USER',100000);
INSERT INTO user_roles (role,user_id) VALUES ('ADMIN',100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Breakfast', 500, 100000),
       ('2020-01-30 13:00:00', 'Lunch', 1000, 100000),
       ('2020-01-30 20:00:00', 'Dinner', 500, 100000),
       ('2020-01-31 0:00:00', 'Border', 100, 100000),
       ('2020-01-31 10:00:00', 'Breakfast', 500, 100000),
       ('2020-01-31 13:00:00', 'Lunch', 1000, 100000),
       ('2020-01-31 20:00:00', 'Dinner', 510, 100000),
       ('2020-01-31 14:00:00', 'Admin lunch', 510, 100001),
       ('2020-01-31 21:00:00', 'Admin dinner', 1500, 100001);
