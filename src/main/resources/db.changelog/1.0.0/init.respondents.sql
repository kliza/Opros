create table if not exists respondents
(
    id      uuid primary key,
    fio     text,
    email   text,
    poll_id uuid references polls on delete cascade
);

comment on table respondents is 'Респонденты';
comment on column respondents.fio is 'ФИО';
comment on column respondents.email is 'Email';
comment on column respondents.poll_id is 'Опрос';