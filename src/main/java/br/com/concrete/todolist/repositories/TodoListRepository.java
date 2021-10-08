package br.com.concrete.todolist.repositories;

import br.com.concrete.todolist.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TodoListRepository extends JpaRepository<Todo, BigInteger> {
}
