package br.com.concrete.todolist;

import java.util.List;

public interface RepoTarefaDao {

    Tarefa salvar(Tarefa tarefa);
    Tarefa excluir(Tarefa tarefa);

    List<Tarefa> findAll();

}
