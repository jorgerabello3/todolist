package br.com.concrete.todolist.repositories;

import br.com.concrete.todolist.models.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoListRepository implements ITodoListRepository {

    List<Todo> todos = new ArrayList<>();

    @Override
    public Todo save(Todo todo) {
        todo.setId(UUID.randomUUID());
        todos.add(todo);
        return todos.get(todos.size() - 1);
    }

    @Override
    public List<Todo> findAll() {
        return todos;
    }

    @Override
    public Todo findById(UUID uuid) {
        return foundId(uuid);
    }

    @Override
    public Todo update(Todo todo) {
        todos.remove(foundId(todo.getId()));
        todos.add(todo);
        return todos.get(todos.size()-1);

    }

    @Override
    public Todo delete(UUID id) {
        todos.remove(foundId(id));
        return foundId(id);
    }


    public Todo foundId(UUID uuid) {
        for (Todo todo : todos) {
            if (todo.getId().equals(uuid)) {
                return todo;
            }
        }
        return null;
    }
}
