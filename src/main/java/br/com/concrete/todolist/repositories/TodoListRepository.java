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
    public Todo findById(UUID id) {
        for (Todo todo : todos) {
            if (todo.getId().equals(id)) {
                return todo;
            }
        }
        return null;
    }

    @Override
    public Todo update(Todo todo) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == todo.getId()) {
                todos.set(i, todo);

                return todos.get(i);
            }
        }
        return null;
    }


    @Override
    public Todo delete(Todo todo) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == todo.getId()) {
                return todos.remove(i);
            }
        }
        return null;
    }
}
