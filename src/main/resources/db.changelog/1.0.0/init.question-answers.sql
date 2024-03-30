create table if not exists question_answers
(
    id          uuid primary key,
    question_id uuid    not null references questions on delete cascade,
    value       text    not null,
    type        varchar not null
);

create index if not exists question_answers_question_id_index
    on public.question_answers (question_id);

comment on table question_answers is 'Параметры ответа на вопрос';
comment on column question_answers.question_id is 'Вопрос';
comment on column question_answers.value is 'Значение ответа';
comment on column question_answers.type is 'Тип ответа';