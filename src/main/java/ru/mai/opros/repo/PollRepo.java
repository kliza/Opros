package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.Poll;

import java.util.UUID;

public interface PollRepo extends JpaRepository<Poll, UUID> {
}
