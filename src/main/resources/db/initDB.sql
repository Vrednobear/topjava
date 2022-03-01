DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS MEALS;
DROP TABLE IF EXISTS USERS;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;


CREATE SEQUENCE GLOBAL_SEQ START 100000;

CREATE TABLE USERS
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);

CREATE UNIQUE INDEX unique_email ON USERS (email);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE MEALS
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    user_id     INTEGER                           NOT NULL,
    date_time   TIMESTAMP           DEFAULT now() NOT NULL,
    description VARCHAR                           NOT NULL,
    calories    INTEGER                           NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX date_created_idx ON meals (user_id, date_time);
DROP INDEX date_created_idx;