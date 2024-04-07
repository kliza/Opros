package ru.mai.opros.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "poll_pages")
public class PollPage {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "page_number")
    private Integer pageNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @OrderBy("questionNumber ASC")
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollPage pollPage = (PollPage) o;
        return Objects.equals(id, pollPage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PollPage{" +
               "id=" + id +
               ", pageNumber=" + pageNumber +
               '}';
    }
}
