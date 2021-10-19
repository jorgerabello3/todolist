package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.TodoBadRequestException;
import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.models.dtos.TodoDTO;
import br.com.concrete.todolist.repositories.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {

    @Mock
    private TodoListRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TodoListService service;

    private LocalDate startDate;
    private LocalDate endDate;
    private TodoDTO leituraDTO;
    private TodoDTO esportesDTO;
    private Todo leitura;
    private Todo esportes;


    @BeforeEach
    void setup() {
        startDate = LocalDate.now();
        endDate = LocalDate.now();

        leituraDTO = new TodoDTO();

        leituraDTO.setId(new BigInteger("1"));
        leituraDTO.setTitle("Leitura");
        leituraDTO.setDescription("Ler livro sobre TDD");
        leituraDTO.setStartDate(startDate);
        leituraDTO.setEndDate(endDate);

        leitura = new Todo();
        leitura.setId(new BigInteger("1"));
        leitura.setTitle("Leitura");
        leitura.setDescription("Ler livro sobre TDD");
        leitura.setStartDate(startDate);
        leitura.setEndDate(endDate);

        esportesDTO = new TodoDTO();

        esportesDTO.setId(new BigInteger("2"));
        esportesDTO.setTitle("Esportes");
        esportesDTO.setDescription("Praticar Muay Thai");
        esportesDTO.setStartDate(startDate);
        esportesDTO.setEndDate(endDate);

        esportes = new Todo();
        esportes.setId(new BigInteger("2"));
        esportes.setTitle("Esportes");
        esportes.setDescription("Praticar Muay Thai");
        esportes.setStartDate(startDate);
        esportes.setEndDate(endDate);

    }

    @Test
    void GivenATodoWhenTryToSaveThenShouldSaveAndReturnSavedTodoData() {

        when(modelMapper.map(leituraDTO,Todo.class)).thenReturn(leitura);
        when(repository.save(leitura)).thenReturn(leitura);
        when(modelMapper.map(leitura,TodoDTO.class)).thenReturn(leituraDTO);

        TodoDTO savedTodo = service.save(leituraDTO);


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
    void GivenARequestToFindAllTodosWhenTryToRetrieveThenShouldReturnAnEmptyList() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Todo> allTodos = service.findAll();

        assertThat(allTodos).isEmpty();


    }


    @Test
    void GivenANIdWhenTryToRetrieveATodoThenShouldReturnCorrectDataForATodo() {

        when(repository.findById(new BigInteger("1"))).thenReturn(Optional.of(leitura));
        TodoDTO foundedTodo = service.findById(new BigInteger("1"));

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

        TodoDTO foundTodo = service.findById(leitura.getId());

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

        service.deleteById(esportes.getId());

        verify(repository).deleteById(esportes.getId());

    }


    @Test
    void GivenAnIdForDeleteThatNotExistsInTheDatabaseWhenShouldThrowTodoNotException() {
        when(repository.findById(new BigInteger("3"))).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.deleteById(new BigInteger("3")));

    }

    @Test
    void GivenAnIdForSaveThatItIsNullWhenShouldReturnSaveDatabaseTodo() {

        boolean ValidateId = service.validIdForSave(null);

        assertThat(ValidateId).isTrue();
    }

    @Test
    void GivenAnIdForSaveThatExistsWhenShouldReturnTodoBadRequestException() {

        when(repository.findById(leitura.getId())).thenReturn(Optional.of(leitura));

        assertThrows(TodoBadRequestException.class, () -> service.save(leituraDTO));
    }
}


