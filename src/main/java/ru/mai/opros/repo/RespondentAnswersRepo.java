package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.RespondentAnswer;

import java.util.UUID;

public interface RespondentAnswersRepo extends JpaRepository<RespondentAnswer, UUID> {
}
