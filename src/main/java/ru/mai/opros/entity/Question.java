package ru.mai.opros.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.mai.opros.entity.enums.AnswerType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "page_id")
    private PollPage page;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AnswerType type;

    @Column(name = "question_number")
    private Integer questionNumber;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("number ASC")
    private List<AnswerParam> answerParams;

    @OneToMany(mappedBy = "question")
    private Set<RespondentAnswer> respondentAnswers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question{" +
               "id=" + id +
               ", value='" + value + '\'' +
               '}';
    }
}
