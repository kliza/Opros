package ru.mai.opros.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mai.opros.dto.RespondentDto;
import ru.mai.opros.entity.Respondent;
import ru.mai.opros.entity.RespondentAnswer;
import ru.mai.opros.repo.AnswerParamsRepo;
import ru.mai.opros.repo.PollRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.repo.RespondentAnswersRepo;
import ru.mai.opros.repo.RespondentRepo;
import ru.mai.opros.service.RespondentService;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RespondentServiceImpl implements RespondentService {
    private final RespondentAnswersRepo respondentAnswersRepo;
    private final QuestionRepo questionRepo;
    private final RespondentRepo respondentRepo;
    private final PollRepo pollRepo;
    private final AnswerParamsRepo answerParamsRepo;

    @Override
    public RespondentDto saveAnswers(UUID id, RespondentDto respondentDto) {
        Respondent respondent = respondentRepo.save(new Respondent()
                .setFio(respondentDto.getFio())
                .setEmail(respondentDto.getEmail())
                .setPoll(pollRepo.getReferenceById(id)));

        respondentDto.getAnswers().stream()
                .filter(answer -> Objects.nonNull(answer.getValue()))
                .forEach(answer -> respondentAnswersRepo.save(new RespondentAnswer()
                        .setRespondent(respondent)
                        .setPoll(respondent.getPoll())
                        .setQuestion(questionRepo.getReferenceById(answer.getQuestionId()))
                        .setAnswerParam(answerParamsRepo.getReferenceById(answer.getId()))
                        .setValue(answer.getValue())));

        return respondentDto;
    }
}
