package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {

    private TodoListService service;
    private LocalDate startDate;
    private LocalDate endDate;
    private Todo leitura;
    private Todo esportes;

    @BeforeEach
    void setup() {
        startDate = LocalDate.now();
        endDate = LocalDate.now();

        leitura = new Todo();

        leitura.setTitle("Leitura");
        leitura.setDescription("Ler livro sobre TDD");
        leitura.setStartDate(startDate);
        leitura.setEndDate(endDate);


        esportes = new Todo();
        esportes.setTitle("Esportes");
        esportes.setDescription("Praticar Muay Thai");
        esportes.setStartDate(startDate);
        esportes.setEndDate(endDate);

        service = new TodoListService();
    }

    @Test
    void GivenATodoWhenTryToSaveThenShouldSaveAndReturnSavedTodoData() {
        Todo savedTodo = service.save(leitura);

        assertNotNull(savedTodo);
        assertEquals("Leitura", savedTodo.getTitle());
        assertEquals("Ler livro sobre TDD", savedTodo.getDescription());
        assertEquals(startDate, savedTodo.getStartDate());
        assertEquals(endDate, savedTodo.getEndDate());
    }

    @Test
    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnAllSavedTodosData() {
        service.save(leitura);
        service.save(esportes);

        List<Todo> allTodos = service.findAll();

        assertNotNull(allTodos);
        assertEquals(2, allTodos.size());
    }

    @Test
    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnCorrectDataForATodo() {
        service.save(leitura);
        service.save(esportes);

        List<Todo> allTodos = service.findAll();

        Todo todo = allTodos.get(0);

        assertEquals("Leitura", todo.getTitle());
        assertEquals("Ler livro sobre TDD", todo.getDescription());
        assertEquals(startDate, todo.getStartDate());
        assertEquals(endDate, todo.getEndDate());
    }

    @Test
    void GivenANIdWhenTryToRetrieveATodoThenShouldReturnCorrectDataForATodo() {
        Todo savedLeitura = service.save(leitura);
        service.save(esportes);

        Todo savedTodo = service.findById(savedLeitura.getId());

        assertEquals("Leitura", savedTodo.getTitle());
        assertEquals("Ler livro sobre TDD", savedTodo.getDescription());
        assertEquals(startDate, savedTodo.getStartDate());
        assertEquals(endDate, savedTodo.getEndDate());
    }

    @Test
    void GivenAnIdThatNotExistsToRetrieveATodosThenShouldThrowTodoNotFoundException() {
        service.save(leitura);
        service.save(esportes);

        assertThrows(TodoNotFoundException.class, () -> service.findById(UUID.fromString("9dabaef0-0aa9-40d7-aa37-4ead13f1ea01")));
    }

    @Test
    void GivenAnIdThatExistsInTheDatabaseWhenShouldUpdateDatabaseTodo() {

        Todo savedTodo = service.save(leitura);

        Todo titleFound = service.findById(savedTodo.getId());
        titleFound.setTitle("New title");

        Todo titleUpdate = service.updateById(titleFound);

        assertEquals("New title", titleUpdate.getTitle());

    }

    @Test
    void GivenAnIdThatExistsInTheDatabaseWhenShoulDeleteIdOfDatabaseTodo() {
        Todo savedTodo = service.save(leitura);

        Todo idFound = service.findById(savedTodo.getId());
        Todo deletedDados = service.deleteById(idFound);

        assertEquals(savedTodo.getId(), deletedDados.getId());

    }

}
