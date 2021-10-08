package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.repositories.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

    private final TodoListRepository repository;

    @Autowired
    public TodoListService(TodoListRepository repository) {
        this.repository = repository;
    }

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Todo findById(BigInteger id) {
        return doesNotExistsIdInTheDatabaseTodo(id);
    }

    public void updateById(Todo todo) {
        doesNotExistsIdInTheDatabaseTodo(todo.getId());
        repository.save(todo);
    }

    public void deleteById(BigInteger id) {
        doesNotExistsIdInTheDatabaseTodo(id);
        repository.deleteById(id);

    }


    public Todo doesNotExistsIdInTheDatabaseTodo(BigInteger id) {
        Optional<Todo> optionalTodo = repository.findById(id);
        return optionalTodo.orElseThrow(() -> new TodoNotFoundException("Todo not found for id " + id));
    }
}
