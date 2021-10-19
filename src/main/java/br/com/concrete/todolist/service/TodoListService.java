package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.TodoBadRequestException;
import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.models.dtos.TodoDTO;
import br.com.concrete.todolist.repositories.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoListService {

    private final TodoListRepository repository;

    private final ModelMapper modelMapper;


    public TodoDTO save(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        if (validIdForSave(todo.getId())) {
            Todo saveTodo = repository.save(todo);
            return modelMapper.map(saveTodo,TodoDTO.class);
        }
        throw new TodoBadRequestException("The Todo id " + todo.getId() + " Id informed exists save in the database, increment id automatic");
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public TodoDTO findById(BigInteger id) {
         return doesNotExistsIdInTheDatabaseTodo(id);
    }

    public void updateById(TodoDTO todoDTO) {
        doesNotExistsIdInTheDatabaseTodo(todoDTO.getId());
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        repository.save(todo);
    }

    public void deleteById(BigInteger id) {
        doesNotExistsIdInTheDatabaseTodo(id);
        repository.deleteById(id);

    }


    public TodoDTO doesNotExistsIdInTheDatabaseTodo(BigInteger id) {
        Optional<Todo> optionalTodo = repository.findById(id);
        Todo todo = optionalTodo.orElseThrow(() -> new TodoNotFoundException("Todo not found for id " + id));

        return modelMapper.map(todo,TodoDTO.class);
    }

    public boolean validIdForSave(BigInteger id) {
        if (id == null) {
            return true;
        }
        Optional<Todo> optionalTodo = repository.findById(id);
        return optionalTodo.isEmpty();
    }
}
