package br.com.concrete.todolist;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockTemporario implements MockTemporarioInter {

    private UUID idRecebido;
    private String tituloRecebido;
    private String descricaoRecebido;
    private String dataInicialRecebido;
    private String dataFinalRecebido;


    @Override
    public void mockAdicionarTemporario(UUID id, String titulo, String descricao, String dataInicial, String dataFinal) {
        idRecebido = id;
        tituloRecebido = titulo;
        descricaoRecebido = descricao;
        dataInicialRecebido = dataInicial;
        dataFinalRecebido = dataFinal;
    }


    public void tarefaAdicionada(UUID idEsperado, String tituloEsperado, String descricaoEsperado, String dataInicialEsperado, String dataFinalEsperado) {
        assertEquals(idEsperado, idRecebido);
        assertEquals(tituloEsperado, tituloRecebido);
        assertEquals(descricaoEsperado, descricaoRecebido);
        assertEquals(dataInicialEsperado, dataInicialRecebido);
        assertEquals(dataFinalEsperado, dataFinalRecebido);
    }
}
