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
        Todo todo = repository.findById(id);
        if (todo != null) {
            return todo;
        }
        throw new TodoNotFoundException("Todo not found for id " + id);
    }

    public Todo updateById(Todo todo) {
        return repository.update(todo);
    }

    public Todo deleteById(Todo todo) {
        return repository.delete(todo);
    }
}
