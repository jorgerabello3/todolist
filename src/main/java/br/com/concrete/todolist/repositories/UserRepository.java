package br.com.concrete.todolist.repositories;

import br.com.concrete.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, BigInteger> {

    List<User> findByName(String firstName);
}

