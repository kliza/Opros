package ru.mai.opros.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.opros.entity.Respondent;

import java.util.UUID;

public interface RespondentRepo extends JpaRepository<Respondent, UUID> {
}
