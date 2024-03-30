create table if not exists respondent_answers
(
    id            uuid primary key,
    respondent_id uuid not null references respondents on delete cascade,
    poll_id       uuid not null references polls on delete cascade,
    question_id   uuid not null references questions on delete cascade
);

create index if not exists respondent_answers_respondent_id_index
    on public.respondent_answers (respondent_id);

create index if not exists respondent_answers_poll_id_index
    on public.respondent_answers (poll_id);

comment on table respondent_answers is 'Ответы респондента';
comment on column respondent_answers.respondent_id is 'Респондент';
comment on column respondent_answers.poll_id is 'Опрос';
