package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.entity.User;

import java.util.List;
import java.util.UUID;

public interface PollRepo extends JpaRepository<Poll, UUID> {

    List<Poll> findAllByOwner(User owner);
}
