package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.Question;

import java.util.UUID;

public interface QuestionRepo extends JpaRepository<Question, UUID> {
}
