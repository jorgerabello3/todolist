package br.com.concrete.todolist;

import java.util.ArrayList;
import java.util.List;

public class TarefaDao implements RepoTarefaDao {

    public static List<Tarefa> listaTarefa = new ArrayList<>();


    @Override
    public Tarefa salvar(Tarefa tarefa) {
        listaTarefa.add(tarefa);
        return tarefa;
    }


    public Tarefa atualizar(int i, Tarefa tarefa) {
        listaTarefa.set(i, tarefa);
        return tarefa;
    }

    @Override
    public Tarefa excluir(Tarefa tarefa) {
        listaTarefa.remove(tarefa);
        return tarefa;
    }


    @Override
    public List<Tarefa> findAll() {
        return listaTarefa;
    }


}
