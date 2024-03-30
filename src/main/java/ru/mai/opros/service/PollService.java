package ru.mai.opros.service;

import org.springframework.data.domain.Pageable;
import ru.mai.opros.generated.dto.PagedPollsResponse;
import ru.mai.opros.generated.dto.PollAnalytic;
import ru.mai.opros.generated.dto.PollDto;
import ru.mai.opros.generated.dto.PollStat;

import java.util.UUID;

public interface PollService {
    PollDto createNewPoll();

    void deletePoll(UUID id);

    PagedPollsResponse getAll(Pageable pageable);

    PollAnalytic getAnalytic(UUID id);

    PollDto getPoll(UUID id);

    PollStat getStat(UUID id);
}
