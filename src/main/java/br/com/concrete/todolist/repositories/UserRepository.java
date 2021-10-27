package br.com.concrete.todolist.repositories;

import br.com.concrete.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {

    List<User> findByFirstName(String firstName);
    List<User> findByCpf(String cpf);

}
