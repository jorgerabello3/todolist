package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.errors.exception.TodoExceptionDetails;
import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.models.dtos.TodoDTO;
import br.com.concrete.todolist.service.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "All Todo", description = "List all Todo in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo find success")})
    public ResponseEntity<List<Todo>> listAll() {
        return ResponseEntity.ok(todoListService.findAll());
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Todo for Id", description = "Find Todo for Id in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo find success"),
            @ApiResponse(responseCode = "400", description = "Failure to find Todo"),
            @ApiResponse(responseCode = "404", description = "Todo not find")})
    public ResponseEntity<TodoDTO> findById(@PathVariable BigInteger id) {
        return ResponseEntity.ok(todoListService.findById(id));
    }


    @PostMapping
    @Operation(summary = "Save Todo", description = "Save Todo new database" /*, tags = {"todos"}*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo registration success"),
            @ApiResponse(responseCode = "400", description = "Failure registration Todo", content = @Content(schema = @Schema(implementation = TodoExceptionDetails.class)))
    })
    public ResponseEntity<TodoDTO> save(@Valid @RequestBody TodoDTO todoDTO) {
        return new ResponseEntity<>(todoListService.save(todoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update Todo", description = "Update Todo for Id in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo update success"),
            @ApiResponse(responseCode = "404", description = "Todo not find in the database")})
    public ResponseEntity<Void> update(@RequestBody TodoDTO todoDTO) {
        todoListService.updateById(todoDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete Todo", description = "Delete Todo for Id in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo delete success"),
            @ApiResponse(responseCode = "404", description = "Todo not find in the database")})
    public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
        todoListService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
