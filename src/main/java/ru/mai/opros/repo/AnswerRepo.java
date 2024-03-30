package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.AnswerParam;

import java.util.UUID;

public interface AnswerRepo extends JpaRepository<AnswerParam, UUID> {
}
