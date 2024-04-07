package ru.mai.opros.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import ru.mai.opros.dto.PollAnalytic;
import ru.mai.opros.dto.PollDto;
import ru.mai.opros.dto.PollStat;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.entity.PollPage;

import java.util.List;
import java.util.UUID;

public interface PollService {
    Poll createNewPoll();

    void deletePoll(UUID id);

    Page<Poll> getAll(Pageable pageable);

    PollAnalytic getAnalytic(UUID id);

    PollDto getPoll(UUID id);

    PollStat getStat(UUID id);

    PollPage addPage(UUID id);

    List<Poll> getAll(Authentication authentication);

    UUID getPollIdByPageId(UUID pageId);

    UUID getPollIdByQuestionId(UUID id);

    PollDto updatePoll(UUID id, PollDto poll, Authentication authentication);
}
