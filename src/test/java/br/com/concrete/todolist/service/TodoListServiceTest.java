package br.com.concrete.todolist.service;
import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import br.com.concrete.todolist.repositories.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {
    @Mock
    private TodoListRepository repository;

    @InjectMocks
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
    }
    //    @Test
//    void GivenATodoWhenTryToSaveThenShouldSaveAndReturnSavedTodoData() {
//        Todo savedTodo = service.save(leitura);
//
//        assertNotNull(savedTodo);
//        assertEquals("Leitura", savedTodo.getTitle());
//        assertEquals("Ler livro sobre TDD", savedTodo.getDescription());
//        assertEquals(startDate, savedTodo.getStartDate());
//        assertEquals(endDate, savedTodo.getEndDate());
//    }
//    @Test
//    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnAllSavedTodosData() {
//        service.save(leitura);
//        service.save(esportes);
//
//        List<Todo> allTodos = service.findAll();
//
//        assertThat(allTodos).isNotNull();
//        assertThat(allTodos.size()).isEqualTo(2);
//
//    }
//    @Test
//    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnCorrectDataForATodo() {
//        service.save(leitura);
//        service.save(esportes);
//
//        List<Todo> allTodos = service.findAll();
//
//        Todo todo = allTodos.get(0);
//
//        assertEquals("Leitura", todo.getTitle());
//        assertEquals("Ler livro sobre TDD", todo.getDescription());
//        assertEquals(startDate, todo.getStartDate());
//        assertEquals(endDate, todo.getEndDate());
//    }
    @Test
    void GivenANIdWhenTryToRetrieveATodoThenShouldReturnCorrectDataForATodo() {
        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(leitura));
        Todo foundedTodo = service.findById(new BigInteger("1"));
        assertThat(foundedTodo).isNotNull();
        assertThat(foundedTodo.getTitle()).isEqualTo("Leitura");
        assertThat(foundedTodo.getDescription()).isEqualTo("Ler livro sobre TDD");
        assertThat(foundedTodo.getStartDate()).isEqualTo(startDate);
        assertThat(foundedTodo.getEndDate()).isEqualTo(endDate);
    }
    @Test
    void GivenAnIdThatNotExistsToRetrieveATodosThenShouldThrowTodoNotFoundException() {
        when(repository.findById(new BigInteger("19990"))).thenReturn(Optional.empty());
        assertThrows(TodoNotFoundException.class, () -> service.findById(new BigInteger("19990")));
    }
//    @Test
//    void GivenAnIdThatExistsInTheDatabaseWhenShouldUpdateDatabaseTodo() {
//
//        Todo savedTodo = service.save(leitura);
//
//        Todo titleFound = service.findById(savedTodo.getId());
//        titleFound.setTitle("New title");
//
//        Todo titleUpdate = service.updateById(titleFound);
//
//
//        assertEquals("New title", titleUpdate.getTitle());
//    }
//    @Test
//    void GivenAnIdForUpdateThatNotExistsInTheDatabaseWhenShouldThrowTodoNotException() {
//        service.save(leitura);
//        service.save(esportes);
//
//        assertThrows(TodoNotFoundException.class, () -> service.findById(UUID.fromString("9dabaef0-0aa9-40d7-aa37-4ead13f1ea01")));
//    }
//    @Test
//    void GivenAnIdThatExistsInTheDatabaseWhenShouldDeleteIdOfDatabaseTodo() {
//        Todo savedTodo = service.save(leitura);
//
//        Todo idFound = service.findById(savedTodo.getId());
//        Todo deletedData = service.deleteById(idFound.getId());
//
//        assertThat(deletedData).isNull();
//    }
//    @Test
//    void GivenAnIdForDeleteThatNotExistsInTheDatabaseWhenShouldThrowTodoNotException() {
//        service.save(leitura);
//        service.save(esportes);
//
//        assertThrows(TodoNotFoundException.class, () -> service.deleteById(UUID.fromString("9dabaef0-0aa9-40d7-aa37-4ead13f1ea01")));
//
//    }
}

