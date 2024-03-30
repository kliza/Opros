create table if not exists questions
(
    id      uuid primary key,
    page_id uuid not null references poll_pages on delete cascade,
    value   text not null
);

create index if not exists questions_page_id_index
    on public.questions (page_id);

comment on table questions is 'Вопросы';
comment on column questions.page_id is 'Страница опроса';
comment on column questions.value is 'Текст вопроса';
