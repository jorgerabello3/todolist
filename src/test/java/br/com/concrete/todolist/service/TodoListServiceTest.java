package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.repositories.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        leitura.setId(new BigInteger("1"));
        leitura.setTitle("Leitura");
        leitura.setDescription("Ler livro sobre TDD");
        leitura.setStartDate(startDate);
        leitura.setEndDate(endDate);

        esportes = new Todo();

        esportes.setId(new BigInteger("2"));
        esportes.setTitle("Esportes");
        esportes.setDescription("Praticar Muay Thai");
        esportes.setStartDate(startDate);
        esportes.setEndDate(endDate);
    }

    @Test
    void GivenATodoWhenTryToSaveThenShouldSaveAndReturnSavedTodoData() {

        when(repository.save(leitura)).thenReturn(leitura);
        Todo savedTodo = service.save(leitura);


        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo.getTitle()).isEqualTo("Leitura");
        assertThat(savedTodo.getDescription()).isEqualTo("Ler livro sobre TDD");
        assertThat(startDate).isEqualTo(savedTodo.getStartDate());
        assertThat(endDate).isEqualTo(savedTodo.getEndDate());

    }

    @Test
    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnAllSavedTodosData() {

        when(repository.findAll()).thenReturn(List.of(leitura, esportes));

        List<Todo> allTodos = service.findAll();

        assertThat(allTodos).isNotNull();
        assertThat(allTodos.size()).isEqualTo(2);

    }

    @Test
    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnCorrectDataForATodo() {

        when(repository.findAll()).thenReturn(List.of(leitura, esportes));

        List<Todo> allTodos = service.findAll();

        Todo todo = allTodos.get(0);

        assertThat(todo.getTitle()).isEqualTo("Leitura");
        assertThat(todo.getDescription()).isEqualTo("Ler livro sobre TDD");
        assertThat(todo.getStartDate()).isEqualTo(startDate);
        assertThat(todo.getEndDate()).isEqualTo(endDate);

    }


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

    @Test
    void GivenAnIdThatExistsInTheDatabaseWhenShouldUpdateDatabaseTodo() {

        when(repository.findById(leitura.getId())).thenReturn(Optional.of(leitura));

        Todo foundTodo = service.findById(leitura.getId());

        foundTodo.setTitle("New title");

        service.updateById(foundTodo);

        assertThat(leitura.getTitle()).isEqualTo("New title");
    }


    @Test
    void GivenAnIdForUpdateThatNotExistsInTheDatabaseWhenShouldThrowTodoNotException() {
        when(repository.findById(new BigInteger("3"))).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.findById(new BigInteger("3")));
    }

    @Test
    void GivenAnIdThatExistsInTheDatabaseWhenShouldDeleteIdOfDatabaseTodo() {
        when(repository.findById(esportes.getId())).thenReturn(Optional.of(esportes));

        Todo idFound = service.findById(esportes.getId());
        Todo deletedData = service.deleteById(idFound.getId());

        assertThat(deletedData).isNull();

    }


    @Test
    void GivenAnIdForDeleteThatNotExistsInTheDatabaseWhenShouldThrowTodoNotException() {
        when(repository.findById(new BigInteger("3"))).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.deleteById(new BigInteger("3")));

    }
}

