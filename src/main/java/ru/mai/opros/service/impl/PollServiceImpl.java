package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mai.opros.dto.AnswerAnalytic;
import ru.mai.opros.dto.PageAnalytic;
import ru.mai.opros.dto.PollAnalytic;
import ru.mai.opros.dto.PollDto;
import ru.mai.opros.dto.QuestionAnalytic;
import ru.mai.opros.entity.AnswerParam;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.entity.PollPage;
import ru.mai.opros.entity.Question;
import ru.mai.opros.entity.RespondentAnswer;
import ru.mai.opros.entity.User;
import ru.mai.opros.entity.enums.AnswerType;
import ru.mai.opros.repo.PollRepo;
import ru.mai.opros.service.PollService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepo pollRepo;
    private final ModelMapper mapper;

    @Override
    public Poll createNewPoll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return pollRepo.save(new Poll()
                .setOwner((User) authentication.getPrincipal()));
    }

    @Override
    public void deletePoll(UUID id) {
        pollRepo.deleteById(id);
    }

    @Override
    public PollAnalytic getPollAnalytic(UUID id) {
        return pollRepo.findById(id)
                .map(poll -> new PollAnalytic()
                        .setPollName(poll.getName())
                        .setRespondentsCount(poll.getRespondents().size())
                        .setPages(getPagesAnalytic(poll)))
                .orElseThrow(() -> new EntityNotFoundException("Опрос %s не найден".formatted(id)));
    }

    private List<PageAnalytic> getPagesAnalytic(Poll poll) {
        return poll.getPages().stream()
                .map(page -> new PageAnalytic()
                        .setNumber(page.getPageNumber())
                        .setQuestions(getQuestionsAnalytic(page)))
                .toList();
    }

    private List<QuestionAnalytic> getQuestionsAnalytic(PollPage page) {
        return page.getQuestions().stream()
                .map(question -> new QuestionAnalytic()
                        .setNumber(question.getQuestionNumber())
                        .setType(question.getType())
                        .setText(question.getValue())
                        .setAnswers(getAnswersAnalytic(question)))
                .toList();
    }

    private List<AnswerAnalytic> getAnswersAnalytic(Question question) {
        List<AnswerAnalytic> list;
        if (question.getType().equals(AnswerType.SCALE)) {
            list = question.getRespondentAnswers().stream()
                    .collect(Collectors.groupingBy(RespondentAnswer::getValue, Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> new AnswerAnalytic()
                            .setText(entry.getKey())
                            .setAnswersCount(entry.getValue().intValue()))
                    .toList();
        } else {
            list = question.getRespondentAnswers().stream()
                    .collect(Collectors.groupingBy(answer -> answer.getAnswerParam().getValue(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> new AnswerAnalytic()
                            .setText(entry.getKey())
                            .setAnswersCount(entry.getValue().intValue()))
                    .toList();
        }

        log.info(""+list);

        return list;
    }

    @Override
    public PollDto getPoll(UUID id) {
        return pollRepo.findById(id)
                .map(poll -> mapper.map(poll, PollDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Опрос %s не найден".formatted(id)));
    }

    @Override
    public List<Poll> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return pollRepo.findAllByOwner(user);
    }

    @Override
    public PollDto updatePoll(UUID id, PollDto pollDto, Authentication authentication) {
        log.info("{}", pollDto);
        Poll poll = pollRepo.findById(id)
                .map(p -> {
                    Map<Integer, PollPage> pagesToSave = pollDto.getPages().stream()
                            .map(page -> mapper.map(page, PollPage.class))
                            .collect(Collectors.toMap(PollPage::getPageNumber, Function.identity()));
                    Map<Integer, PollPage> existingPages = p.getPages().stream()
                            .collect(Collectors.toMap(PollPage::getPageNumber, Function.identity()));

                    pagesToSave.forEach((pageNum, pageToSave) -> {
                        if (existingPages.containsKey(pageNum)) {
                            PollPage existingPage = existingPages.get(pageNum);
                            Map<Integer, Question> existingQuestions = existingPage.getQuestions().stream()
                                    .collect(Collectors.toMap(Question::getQuestionNumber, Function.identity()));
                            Map<Integer, Question> questionsToSave = pageToSave.getQuestions().stream()
                                    .collect(Collectors.toMap(Question::getQuestionNumber, Function.identity()));

                            if (existingQuestions.size() > questionsToSave.size()) {
                                existingQuestions.forEach((questionNumber, question) -> {
                                    if (!questionsToSave.containsKey(questionNumber)) {
                                        existingPage.getQuestions().remove(question);
                                    }
                                });
                            }

                            questionsToSave.forEach((questionNumber, questionToSave) -> {
                                if (existingQuestions.containsKey(questionNumber)) {
                                    Question existingQuestion = existingQuestions.get(questionNumber);
                                    existingQuestion
                                            .setValue(questionToSave.getValue())
                                            .setType(questionToSave.getType());

                                    Map<Integer, AnswerParam> existingParams = existingQuestion.getAnswerParams().stream()
                                            .collect(Collectors.toMap(AnswerParam::getNumber, Function.identity()));
                                    Map<Integer, AnswerParam> paramsToSave = questionToSave.getAnswerParams().stream()
                                            .collect(Collectors.toMap(AnswerParam::getNumber, Function.identity()));

                                    paramsToSave.forEach((num, answer) -> {
                                        if (existingParams.containsKey(num)) {
                                            existingParams.get(num).setValue(answer.getValue());
                                        } else {
                                            existingQuestion.getAnswerParams().add(answer);
                                        }
                                    });

                                } else {
                                    existingPage.getQuestions().add(questionToSave);
                                }
                            });
                        } else {
                            p.getPages().add(pageToSave);
                        }
                    });

                    p.getPages().forEach(page -> {
                        page.setPoll(p);
                        page.getQuestions().forEach(question -> {
                            question.setPage(page);
                            question.getAnswerParams().forEach(answerParam -> answerParam.setQuestion(question));
                        });
                    });
                    p.setName(pollDto.getName());

                    return pollRepo.save(p);
                })
                .orElseThrow(() -> new EntityNotFoundException("Опрос %s не найден".formatted(id)));

        return mapper.map(poll, PollDto.class);
    }
}
