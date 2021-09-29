package br.com.concrete.todolist.repositories;

import br.com.concrete.todolist.models.Todo;

import java.util.List;
import java.util.UUID;

public interface ITodoListRepository {
    Todo save(Todo todo);

    List<Todo> findAll();

    Todo findById(UUID id);

    Todo update(Todo todo);

    Todo delete(UUID id);


}
