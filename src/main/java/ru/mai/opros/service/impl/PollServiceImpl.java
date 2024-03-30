package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.entity.Question;
import ru.mai.opros.entity.Respondent;
import ru.mai.opros.entity.RespondentAnswer;
import ru.mai.opros.entity.User;
import ru.mai.opros.generated.dto.PagedPollsResponse;
import ru.mai.opros.generated.dto.PollAnalytic;
import ru.mai.opros.generated.dto.PollDto;
import ru.mai.opros.generated.dto.PollPageDto;
import ru.mai.opros.generated.dto.PollStat;
import ru.mai.opros.generated.dto.QuestionAnswer;
import ru.mai.opros.generated.dto.QuestionStat;
import ru.mai.opros.generated.dto.RespondentAnswers;
import ru.mai.opros.generated.dto.RespondentDto;
import ru.mai.opros.repo.PollRepo;
import ru.mai.opros.service.PollService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepo pollRepo;
    private final ModelMapper mapper;

    @Override
    public PollDto createNewPoll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Poll poll = pollRepo.save(new Poll()
                .setOwner((User) authentication.getPrincipal()));

        return mapper.map(poll, PollDto.class);
    }

    @Override
    public void deletePoll(UUID id) {
        pollRepo.deleteById(id);
    }

    @Override
    public PagedPollsResponse getAll(Pageable pageable) {
        Page<Poll> pollPage = pollRepo.findAll(pageable);

        return new PagedPollsResponse()
                .content(pollPage.stream()
                        .map(product -> mapper.map(product, PollDto.class))
                        .toList())
                .pageable(pageable)
                .last(pollPage.isLast())
                .first(pollPage.isFirst())
                .empty(pollPage.isEmpty())
                .number(pollPage.getNumber())
                .numberOfElements(pollPage.getNumberOfElements())
                .size(pollPage.getSize())
                .sort(pollPage.getSort())
                .totalElements(pollPage.getTotalElements())
                .totalPages(pollPage.getTotalPages());
    }

    @Override
    public PollAnalytic getAnalytic(UUID id) {
        Map<Respondent, List<RespondentAnswer>> respondentsWithAnswers = pollRepo.findById(id)
                .map(Poll::getPages).stream()
                .flatMap(Set::stream)
                .flatMap(pollPage -> pollPage.getQuestions().stream())
                .flatMap(question -> question.getRespondentAnswers().stream())
                .collect(Collectors.groupingBy(RespondentAnswer::getRespondent));
        List<RespondentAnswers> respondents = new ArrayList<>();

        respondentsWithAnswers.forEach((respondent, answers) -> respondents.add(new RespondentAnswers()
                .respondent(mapper.map(respondent, RespondentDto.class))
                .answers(answers.stream()
                        .map(respondentAnswer -> mapper.map(respondentAnswer, QuestionAnswer.class))
                        .toList())));

        return new PollAnalytic()
                .respondents(respondents);
    }

    @Override
    public PollDto getPoll(UUID id) {
        return pollRepo.findById(id)
                .map(poll -> mapper.map(poll, PollDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Опрос %s не найден".formatted(id)));
    }

    @Override
    public PollStat getStat(UUID id) {
        List<Question> questions = pollRepo.findById(id)
                .map(Poll::getPages).stream()
                .flatMap(Set::stream)
                .flatMap(pollPage -> pollPage.getQuestions().stream())
                .toList();
        long respondentsCount = questions.stream()
                .flatMap(question -> question.getRespondentAnswers().stream())
                .map(RespondentAnswer::getRespondent)
                .distinct()
                .count();
        List<QuestionStat> questionStats = questions.stream()
                .map(question -> new QuestionStat()
                        .value(question.getValue())
                        .answersCount(question.getRespondentAnswers().size()))
                .toList();

        return new PollStat()
                .respondentsCount((int) respondentsCount)
                .questionStat(questionStats);
    }
}
