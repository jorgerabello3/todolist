package br.com.concrete.todolist.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @NotBlank(message = "The first name can not is empty")
    private String firstName;


    @NotBlank(message = "The last name can not is empty")
    private String lastName;


    @CPF(message = "Cpf invalid")
    private String cpf;

    @Size(min = 6, max = 12, message = "The password must have a minimum of 6 and a maximum of 12 character")
    @NotBlank(message = "The password can not is empty")
    private String password;

    @Column(name = "birth_Date")
    private LocalDate birthDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
