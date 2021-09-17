package br.com.concrete.todolist;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void listaVazia() {
        Tarefa tarefa = new Tarefa();
        assertTrue(tarefa.listaDeTarefas());

    }

    @Test
    void existeId() {
        TarefaDao tarefaDao = new TarefaDao();
        Tarefa tarefa = new Tarefa();
        Tarefa tarefa1 = new Tarefa(UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7eab"), "DESENVOLVEDOR", "JAVA", "15/03/2021", "10/09/2038");
        tarefaDao.salvar(tarefa1);
        assertTrue(tarefa.existeId(tarefa1.getId()));
        UUID id = UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7faf");
        assertFalse(tarefa.existeId(id));

    }


    @Test
    void existeTitulo() {
        Tarefa tarefa = new Tarefa();
        TarefaDao tarefaDao = new TarefaDao();
        Tarefa tarefa1 = new Tarefa(UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7eab"), "DESENVOLVEDOR", "JAVA", "15/03/2021", "10/09/2038");
//        tarefaDao.salvar(tarefa1);
        assertTrue(tarefa.existeTitulo(tarefa1.getTitulo()));


    }

    @Test
    void existeDescricao() {
        Tarefa tarefa = new Tarefa();
        Tarefa tarefa1 = new Tarefa(UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7eab"), "DESENVOLVEDOR", "JAVA", "15/03/2021", "10/09/2038");
        assertTrue(tarefa.existeDescricao(tarefa1.getDescricao()));

    }

    @Test
    void validarData() {
        Tarefa tarefa = new Tarefa();
        assertTrue(tarefa.validarData("02/10/2021"));
        assertFalse(tarefa.validarData("30/02/2021"));

    }

    @Test
    void validarAno() {
        Tarefa tarefa = new Tarefa();
        Tarefa tarefa2 = new Tarefa(UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7eab"), "desenlvolver", "java", "02/10/2021", "02/10/2022");
        assertTrue(tarefa.validarAno(tarefa2));


    }

    @Test
    void dataFinalNaoPodeSerMenorQueDataInicial() {
        Tarefa tarefa = new Tarefa();
        Tarefa tarefa2 = new Tarefa(UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7eab"), "desenlvolver", "java", "02/10/2021", "01/10/2021");
        assertTrue(tarefa.dataFinalMenorQueDataInicial(tarefa2));
    }


}