package br.com.concrete.todolist;

import java.util.UUID;

public interface MockTemporarioInter {

    public void mockAdicionarTemporario(UUID id, String titulo, String descricao, String dataInicial, String dataFinal);
}
