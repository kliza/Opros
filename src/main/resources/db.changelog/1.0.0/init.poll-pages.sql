create table if not exists poll_pages
(
    id          uuid primary key,
    poll_id     uuid not null references polls on delete cascade,
    page_number int not null default 0
);

create index if not exists poll_pages_poll_id_index
    on poll_pages (poll_id);

comment on table poll_pages is 'Страницы опроса';
comment on column poll_pages.poll_id is 'Опрос';
comment on column poll_pages.page_number is 'Номер страницы';
