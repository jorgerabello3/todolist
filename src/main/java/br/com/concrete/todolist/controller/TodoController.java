package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.models.dtos.TodoDTO;
import br.com.concrete.todolist.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Todo>> listAll() {
        return ResponseEntity.ok(todoListService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TodoDTO> findById(@PathVariable BigInteger id) {
        return ResponseEntity.ok(todoListService.findById(id));
    }


    @PostMapping
    public ResponseEntity<TodoDTO> save(@Valid @RequestBody TodoDTO todoDTO) {
        return new ResponseEntity<>(todoListService.save(todoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody TodoDTO todoDTO) {
        todoListService.updateById(todoDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
        todoListService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
