package br.com.concrete.todolist.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private BigInteger id;
    private String title;
    private String description;
    @JsonProperty(value = "start_date")
    private LocalDate startDate;
    @JsonProperty(value = "end_date")
    private LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TodoDTO todo = (TodoDTO) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
