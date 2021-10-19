package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.models.Todo;
import br.com.concrete.todolist.models.dtos.TodoDTO;
import br.com.concrete.todolist.service.TodoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
    void GivenARequestToListAllTodosThenShouldReturnAllTodos() {

        when(service.findAll()).thenReturn(List.of(leitura, esportes));

        List<Todo> allTodo = controller.listAll().getBody();

        assertThat(allTodo).isNotNull();
        assertThat(allTodo).hasSize(2);
        assertThat(allTodo.get(0).getId()).isEqualTo(leituraDTO.getId());
        assertThat(allTodo.get(0).getTitle()).isEqualTo(leituraDTO.getTitle());
        assertThat(allTodo.get(0).getDescription()).isEqualTo(leituraDTO.getDescription());

    }

    @Test
    void GivenARequestForFindTodosIsIfListEmptyThenShouldReturnListEmpty() {

        when(service.findAll()).thenReturn(Collections.emptyList());

        List<Todo> allTodo = controller.listAll().getBody();

        assertThat(allTodo).isEmpty();

    }

    @Test
    void GivenAnIdWhenTryToRetrieveATodoThenShouldReturnCorrectDataForATodo() {

        when(service.findById(ArgumentMatchers.any())).thenReturn(leituraDTO);

        BigInteger expectedId = leituraDTO.getId();

        TodoDTO localizeId = controller.findById(new BigInteger("1")).getBody();

        assertThat(localizeId).isNotNull();

        assertThat(localizeId.getId()).isEqualTo(expectedId);

    }


    @Test
    void GivenATodoForSaveThenShouldSaveAndReturnSavedTodoData() {

        when(service.save(leituraDTO)).thenReturn(leituraDTO);
        TodoDTO savedTodo = controller.save(leituraDTO).getBody();

        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo.getId()).isEqualTo(leituraDTO.getId());
        assertThat(savedTodo.getTitle()).isEqualTo(leituraDTO.getTitle());
        assertThat(savedTodo.getDescription()).isEqualTo(leituraDTO.getDescription());

    }

    @Test
    void GivenAIdForUpdateThatExistWhenShouldUpdateTodo() {
        when(service.findById(leituraDTO.getId())).thenReturn(leituraDTO);

        TodoDTO foundTodo = service.findById(leituraDTO.getId());

        foundTodo.setTitle("New title");

        controller.update(foundTodo);

        assertThat(leituraDTO.getTitle()).isEqualTo("New title");

    }

    @Test
    void GivenAIdForDeleteThatExistWhenShouldDeleteTodo() {

        when(service.save(esportesDTO)).thenReturn(esportesDTO);

        TodoDTO todoSave = service.save(esportesDTO);

        service.deleteById(todoSave.getId());

        TodoDTO todoDeleted = service.findById(todoSave.getId());

        verify(service).deleteById(esportesDTO.getId());
        assertThat(todoDeleted).isNull();
    }

}