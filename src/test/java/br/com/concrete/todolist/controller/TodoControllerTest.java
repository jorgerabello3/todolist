package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.service.TodoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class TodoControllerTest {


    @Mock
    private TodoListService service;

    @InjectMocks
    private TodoController controller;

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
    void GivenARequestToListAllTodosThenShouldReturnAllTodos() {

        when(service.findAll()).thenReturn(List.of(leitura, esportes));

        List<Todo> allTodo = controller.listAll();

        assertThat(allTodo).isNotNull();
        assertThat(allTodo).hasSize(2);
        assertThat(allTodo.get(0).getId()).isEqualTo(leitura.getId());
        assertThat(allTodo.get(0).getTitle()).isEqualTo(leitura.getTitle());
        assertThat(allTodo.get(0).getDescription()).isEqualTo(leitura.getDescription());

    }

    @Test
    void GivenARequestForFindTodosIsIfListEmptyThenShouldReturnListEmpty() {

        when(service.findAll()).thenReturn(Collections.emptyList());

        List<Todo> allTodo = controller.listAll();

        assertThat(allTodo).isEmpty();

    }

    @Test
    void GivenAnIdWhenTryToRetrieveATodoThenShouldReturnCorrectDataForATodo() {

        when(service.findById(new BigInteger("1"))).thenReturn(leitura);
        Todo localizeId = controller.findById(new BigInteger("1"));

        assertThat(localizeId.getId()).isEqualTo(leitura.getId());

    }


    @Test
    void GivenATodoForSaveThenShouldSaveAndReturnSavedTodoData() {

        when(service.save(leitura)).thenReturn(leitura);
        Todo savedTodo = controller.save(leitura);

        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo.getId()).isEqualTo(leitura.getId());
        assertThat(savedTodo.getTitle()).isEqualTo(leitura.getTitle());
        assertThat(savedTodo.getDescription()).isEqualTo(leitura.getDescription());

    }

    @Test
    void GivenAIdForUpdateThatExistWhenShouldUpdateTodo() {
        when(service.findById(leitura.getId())).thenReturn(leitura);

        Todo foundTodo = service.findById(leitura.getId());

        foundTodo.setTitle("New title");

        controller.update(foundTodo);

        assertThat(leitura.getTitle()).isEqualTo("New title");

    }

    @Test
    void GivenAIdForDeleteThatExistWhenShouldDeleteTodo() {
        when(service.findById(leitura.getId())).thenReturn(leitura);

        controller.delete(leitura.getId());

        verify(service).deleteById(leitura.getId());
    }

}