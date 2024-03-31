package ru.mai.opros.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.opros.generated.api.PollApi;
import ru.mai.opros.generated.dto.PagedPollsResponse;
import ru.mai.opros.generated.dto.PollAnalytic;
import ru.mai.opros.generated.dto.PollDto;
import ru.mai.opros.generated.dto.PollPageDto;
import ru.mai.opros.generated.dto.PollStat;
import ru.mai.opros.service.PollService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PollController implements PollApi {
    private final PollService pollService;

    @Override
    public ResponseEntity<PollDto> create() {
        return ResponseEntity.status(201).body(pollService.createNewPoll());
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        pollService.deletePoll(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PagedPollsResponse> getAll(Pageable pageable) {
        return ResponseEntity.ok(pollService.getAll(pageable));
    }

    @Override
    public ResponseEntity<PollAnalytic> getAnalyticById(UUID id) {
        return ResponseEntity.ok(pollService.getAnalytic(id));
    }

    @Override
    public ResponseEntity<PollDto> getById(UUID id) {
        return ResponseEntity.ok(pollService.getPoll(id));
    }

    @Override
    public ResponseEntity<PollStat> getStatById(UUID id) {
        return ResponseEntity.ok(pollService.getStat(id));
    }

    @Override
    public ResponseEntity<PollPageDto> addPage(UUID id) {
        return ResponseEntity.status(201).body(pollService.addPage(id));
    }
}
