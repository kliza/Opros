create table if not exists polls
(
    id       uuid primary key,
    name     text,
    owner_id uuid not null references users on delete cascade
);

create index if not exists polls_owner_id_index
    on polls (owner_id);

comment on table polls is 'Опросы';
comment on column polls.name is 'Название опроса';
comment on column polls.owner_id is 'Владелец опроса';
