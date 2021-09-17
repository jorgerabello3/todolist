package br.com.concrete.todolist;

import java.util.Scanner;
import java.util.UUID;

public class Application {
    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        TarefaDao tarefaDao = new TarefaDao();
        Tarefa tarefa = new Tarefa();

        Tarefa tarefa1 = new Tarefa(UUID.fromString("e75e3676-71c1-4e80-803d-4da6de3f7eab"), "DESENVOLVEDOR", "JAVA", "15/03/2021", "10/09/2038");
        tarefaDao.salvar(tarefa1);


        do {
            Tarefa tarefas = new Tarefa();

            System.out.printf("\nInforme o título da tarefa: ");
            String tituloDigitado = ler.next().concat(ler.nextLine()).toUpperCase();
            tarefas.setTitulo(tituloDigitado);

            System.out.printf("\nInforme a descrição da tarefa:");
            String descricaoDigitada = ler.next().concat(ler.nextLine()).toUpperCase();
            tarefas.setDescricao(descricaoDigitada);

            System.out.printf("\nInforme a data inicial da tarefa:");
            String dataInicialDigitada = ler.next();
            tarefas.setDataInicial(dataInicialDigitada);

            System.out.printf("\nInforme a data final da tarefa:");
            String dataFinalDigitada = ler.next();
            tarefas.setDataFinal(dataFinalDigitada);

            tarefas.cadastrarTarefa(tarefas);

        } while (tarefaDao.listaTarefa.size() < 2);

        tarefa.listaDeTarefas();

        System.out.printf("\n\nInforme o id para realizar a pesquisa:");
        String buscarId = ler.next();
        UUID id =UUID.fromString(buscarId);

        if (tarefa.existeId(id) ){
            tarefa.buscarTarefasPorId(buscarId);
        }else {
            System.out.printf("Id informado não existe no cadastro.");
        }

        System.out.printf("Informe o id que deseja atualizar a descrição:");
         buscarId = ler.next();
        id =UUID.fromString(buscarId);

        System.out.printf("Informe a nova descrição da tarefa:");
        String descricaoAtualizada = ler.next().concat(ler.nextLine()).toUpperCase();
        tarefa.atualizarTarefa(id,descricaoAtualizada);

        System.out.printf("Informe o id da tarefa que deseja excluir:");
        buscarId = ler.next();
        id =UUID.fromString(buscarId);
        tarefa.excluirTarefaDoCadastrpo(id);


    }
}