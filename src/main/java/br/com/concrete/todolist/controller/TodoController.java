package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RequestMapping("todos")
@RequiredArgsConstructor
@RestController

public class TodoController {


    private final TodoListService todoListService;


    @GetMapping
    public List<Todo> listAll() {
        return todoListService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Todo findById(@PathVariable BigInteger id) {
        return todoListService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo save(@Valid @RequestBody Todo todo) {
        return todoListService.save(todo);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Todo todo) {
        todoListService.updateById(todo);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable BigInteger id) {
        todoListService.deleteById(id);
    }


}
