create table if not exists respondents
(
    id    uuid primary key,
    fio   text,
    email text
);

comment on table respondents is 'Респонденты';
comment on column respondents.fio is 'ФИО';
comment on column respondents.email is 'Email';
