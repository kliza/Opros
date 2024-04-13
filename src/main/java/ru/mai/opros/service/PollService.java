package ru.mai.opros.service;

import org.springframework.security.core.Authentication;
import ru.mai.opros.dto.PollAnalytic;
import ru.mai.opros.dto.PollDto;
import ru.mai.opros.entity.Poll;

import java.util.List;
import java.util.UUID;

public interface PollService {
    Poll createNewPoll();

    void deletePoll(UUID id);

    PollAnalytic getPollAnalytic(UUID id);

    PollDto getPoll(UUID id);

    List<Poll> getAll(Authentication authentication);

    PollDto updatePoll(UUID id, PollDto poll, Authentication authentication);
}
