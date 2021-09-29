package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.repositories.TodoListRepository;

import java.util.List;
import java.util.UUID;

public class TodoListService {

    private final TodoListRepository repository = new TodoListRepository();

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Todo findById(UUID id) {
      return doesNotExistsIdInTheDatabaseTodo(id);
    }

    public Todo updateById(Todo todo) {
        return repository.update(todo);
    }

    public Todo deleteById(UUID uuid) {
        doesNotExistsIdInTheDatabaseTodo(uuid);
        return repository.delete(uuid);
    }


    public Todo doesNotExistsIdInTheDatabaseTodo(UUID uuid) {
        Todo todo = repository.foundId(uuid);
        if (todo == null) {
            throw new TodoNotFoundException("Id informed not exists in the database");
        }
        return todo;
    }
}
