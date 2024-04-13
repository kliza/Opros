create table if not exists respondent_answers
(
    id            uuid primary key,
    value         text not null,
    answer_param  uuid not null references answer_params on delete cascade,
    respondent_id uuid not null references respondents on delete cascade,
    poll_id       uuid not null references polls on delete cascade,
    question_id   uuid not null references questions on delete cascade
);

create index if not exists respondent_answers_respondent_id_index
    on respondent_answers (respondent_id);

create index if not exists respondent_answers_poll_id_index
    on respondent_answers (poll_id);

comment on table respondent_answers is 'Ответы респондента';
comment on column respondent_answers.respondent_id is 'Респондент';
comment on column respondent_answers.poll_id is 'Опрос';
comment on column respondent_answers.question_id is 'Вопрос';
comment on column respondent_answers.answer_param is 'Параметр ответа';
comment on column respondent_answers.value is 'Значение ответа';
