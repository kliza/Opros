package ru.mai.opros.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.Respondent;
import ru.mai.opros.entity.RespondentAnswer;
import ru.mai.opros.repo.PollRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.repo.RespondentAnswersRepo;
import ru.mai.opros.repo.RespondentRepo;
import ru.mai.opros.service.RespondentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RespondentServiceImpl implements RespondentService {
    private final RespondentAnswersRepo respondentAnswersRepo;
    private final QuestionRepo questionRepo;
    private final RespondentRepo respondentRepo;
    private final PollRepo pollRepo;
    private final ModelMapper mapper;

    @Override
    public RespondentAnswer addAnswer(UUID questionId) {
//        Respondent respondent = respondentRepo.findById(questionAnswer.getRespondentId())
//                .orElseThrow(() -> new EntityNotFoundException("Респондент %s не найден"
//                        .formatted(questionAnswer.getRespondentId())));
//
//        RespondentAnswer savedAnswer = questionRepo.findById(questionId)
//                .map(question -> new RespondentAnswer()
//                        .setRespondent(respondent)
//                        .setValue(questionAnswer.getValue())
//                        .setPoll(question.getPage().getPoll())
//                        .setQuestion(question))
//                .map(respondentAnswersRepo::save)
//                .orElseThrow(() -> new EntityNotFoundException("Вопрос %s не найден".formatted(questionId)));
//        respondent.getRespondentAnswers().add(savedAnswer);
//
//        return new RespondentAnswers()
//                .respondent(mapper.map(respondent, RespondentDto.class))
//                .answers(respondent.getRespondentAnswers().stream()
//                        .map(respondentAnswer ->
//                        mapper.map(respondentAnswer, QuestionAnswer.class))
//                        .toList());
        return new RespondentAnswer();
    }

    @Override
    public Respondent createRespondent(UUID pollId) {
//        Respondent respondent = respondentRepo.save(new Respondent()
//                .setFio(createRespondentRequest.getFio())
//                .setEmail(createRespondentRequest.getEmail())
//                .setPoll(pollRepo.getReferenceById(pollId)));
        return new Respondent();
    }
}
