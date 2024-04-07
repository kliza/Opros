create table if not exists users
(
    id       uuid primary key,
    login    varchar not null,
    password varchar not null,
    name     varchar,
    role     varchar not null,
    unique (login)
);

create index if not exists users_login_index
    on users (login);

