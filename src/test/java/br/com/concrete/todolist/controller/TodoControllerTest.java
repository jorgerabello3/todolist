package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.service.TodoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        List<Todo> allTodo = controller.listAll().getBody();

        assertThat(allTodo).isNotNull();
        assertThat(allTodo).hasSize(2);
        assertThat(allTodo.get(0).getId()).isEqualTo(leitura.getId());
        assertThat(allTodo.get(0).getTitle()).isEqualTo(leitura.getTitle());
        assertThat(allTodo.get(0).getDescription()).isEqualTo(leitura.getDescription());

    }

    @Test
    void GivenARequestForFindTodosIsIfListEmptyThenShouldReturnListEmpty() {

        when(service.findAll()).thenReturn(Collections.emptyList());

        List<Todo> allTodo = controller.listAll().getBody();

        assertThat(allTodo).isEmpty();

    }

    @Test
    void GivenAnIdWhenTryToRetrieveATodoThenShouldReturnCorrectDataForATodo() {

        when(service.findById(ArgumentMatchers.any())).thenReturn(leitura);

        BigInteger expectedId = leitura.getId();

        Todo localizeId = controller.findById(new BigInteger("1")).getBody();

        assertThat(localizeId).isNotNull();

        assertThat(localizeId.getId()).isEqualTo(expectedId);

    }



    @Test
    void GivenATodoForSaveThenShouldSaveAndReturnSavedTodoData() {

        when(service.save(leitura)).thenReturn(leitura);
        Todo savedTodo = controller.save(leitura).getBody();

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

        when(service.save(esportes)).thenReturn(esportes);

        Todo todoSave = service.save(esportes);

        service.deleteById(todoSave.getId());

        Todo todoDeleted = service.findById(todoSave.getId());

        verify(service).deleteById(esportes.getId());
        assertThat(todoDeleted).isNull();
    }

}