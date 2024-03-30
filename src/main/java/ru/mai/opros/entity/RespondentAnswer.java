package ru.mai.opros.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "respondent_answers")
public class RespondentAnswer {
    @Id
    @GeneratedValue
    private UUID id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "respondent_id")
    private Respondent respondent;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespondentAnswer that = (RespondentAnswer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RespondentAnswer{" +
               "id=" + id +
               ", value='" + value + '\'' +
               '}';
    }
}
