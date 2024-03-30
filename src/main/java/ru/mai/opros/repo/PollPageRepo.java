package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.PollPage;

import java.util.UUID;

public interface PollPageRepo extends JpaRepository<PollPage, UUID> {
}
