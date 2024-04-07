create table if not exists answer_params
(
    id          uuid primary key,
    question_id uuid not null references questions on delete cascade,
    value       text not null,
    number      int
);

create index if not exists question_answers_question_id_index
    on answer_params (question_id);

comment on table answer_params is 'Параметры ответа на вопрос';
comment on column answer_params.question_id is 'Вопрос';
comment on column answer_params.value is 'Значение ответа';
comment on column answer_params.number is 'Порядковый номер ответа в вопросе';