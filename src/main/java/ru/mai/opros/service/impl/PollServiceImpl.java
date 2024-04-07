package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mai.opros.dto.PollAnalytic;
import ru.mai.opros.dto.PollDto;
import ru.mai.opros.dto.PollPageDto;
import ru.mai.opros.dto.PollStat;
import ru.mai.opros.entity.AnswerParam;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.entity.PollPage;
import ru.mai.opros.entity.Question;
import ru.mai.opros.entity.User;
import ru.mai.opros.repo.PollPageRepo;
import ru.mai.opros.repo.PollRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.service.PollService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepo pollRepo;
    private final PollPageRepo pollPageRepo;
    private final QuestionRepo questionRepo;
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
    public Page<Poll> getAll(Pageable pageable) {
        return pollRepo.findAll(pageable);
    }

    @Override
    public PollAnalytic getAnalytic(UUID id) {
        //todo
//        Map<Respondent, List<RespondentAnswer>> respondentsWithAnswers = pollRepo.findById(id)
//                .map(Poll::getPages).stream()
//                .flatMap(Set::stream)
//                .flatMap(pollPage -> pollPage.getQuestions().stream())
//                .flatMap(question -> question.getRespondentAnswers().stream())
//                .collect(Collectors.groupingBy(RespondentAnswer::getRespondent));
//        List<RespondentAnswer> respondents = new ArrayList<>();
//
//        respondentsWithAnswers.forEach((respondent, answers) -> respondents.add(new RespondentAnswers()
//                .respondent(mapper.map(respondent, RespondentDto.class))
//                .answers(answers.stream()
//                        .map(respondentAnswer -> mapper.map(respondentAnswer, QuestionAnswer.class))
//                        .toList())));
//
//        return new PollAnalytic()
//                .respondents(respondents);
        return new PollAnalytic();
    }

    @Override
    public PollDto getPoll(UUID id) {
        return pollRepo.findById(id)
                .map(poll -> mapper.map(poll, PollDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Опрос %s не найден".formatted(id)));
    }

    @Override
    public PollStat getStat(UUID id) {
        //todo
//        List<Question> questions = pollRepo.findById(id)
//                .map(Poll::getPages).stream()
//                .flatMap(Set::stream)
//                .flatMap(pollPage -> pollPage.getQuestions().stream())
//                .toList();
//        long respondentsCount = questions.stream()
//                .filter(question -> CollectionUtils.isNotEmpty(question.getRespondentAnswers()))
//                .flatMap(question -> question.getRespondentAnswers().stream())
//                .map(RespondentAnswer::getRespondent)
//                .filter(Objects::nonNull)
//                .distinct()
//                .count();
//        List<QuestionStat> questionStats = questions.stream()
//                .map(question -> new QuestionStat()
//                        .value(question.getValue())
//                        .answersCount(question.getRespondentAnswers().size()))
//                .toList();
//
//        return new PollStat()
//                .respondentsCount((int) respondentsCount)
//                .questionStat(questionStats);
        return new PollStat();
    }

    @Override
    public PollPage addPage(UUID id) {
        return pollRepo.findById(id)
                .map(poll -> {
                    List<PollPage> pollPages = poll.getPages();
                    PollPage pollPage = new PollPage()
                            .setPageNumber(pollPages.size() + 1)
                            .setPoll(poll);
                    pollPages.add(pollPage);

                    return pollPage;
                })
                .map(pollPageRepo::save)
                .orElseThrow(() -> new EntityNotFoundException("Опрос %s не найден".formatted(id)));
    }

    @Override
    public List<Poll> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return pollRepo.findAllByOwner(user);
    }

    @Override
    public UUID getPollIdByPageId(UUID pageId) {
        return pollPageRepo.findById(pageId)
                .map(PollPage::getPoll)
                .map(Poll::getId)
                .orElseThrow(() -> new EntityNotFoundException("Страница %s не найдена".formatted(pageId)));
    }

    @Override
    public UUID getPollIdByQuestionId(UUID id) {
        return questionRepo.findById(id)
                .map(Question::getPage)
                .map(PollPage::getPoll)
                .map(Poll::getId)
                .orElseThrow(() -> new EntityNotFoundException("Вопрос %s не найден"));
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
                                    existingQuestion.setValue(questionToSave.getValue());
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
