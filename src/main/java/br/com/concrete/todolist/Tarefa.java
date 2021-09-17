package br.com.concrete.todolist;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Tarefa {

    private UUID id;
    private String titulo;
    private String descricao;
    private String dataInicial;
    private String dataFinal;

    TarefaDao tarefaDao = new TarefaDao();


    public Tarefa(UUID id, String titulo, String descricao, String dataInicial, String dataFinal) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Tarefa() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "\nid='" + id + '\'' +
                ", \ntitulo='" + titulo + '\'' +
                ", \ndescricao='" + descricao + '\'' +
                ", \ndataInicial=" + dataInicial +
                ", \ndataFinal=" + dataFinal +
                '}' + "\n\n";
    }

    public void cadastrarTarefa(Tarefa tarefa) {

        id = UUID.randomUUID();

        if (!existeId(id)) {

            if (!existeTitulo(titulo)) {

                if (!existeDescricao(descricao)) {

                    if (validarData(getDataInicial()) && validarAno(tarefa)) {
                        if (validarData(getDataFinal()) && validarAno(tarefa)) {
                            if (!dataFinalMenorQueDataInicial(tarefa)) {
                                tarefaDao.salvar(tarefa);
                            } else {
                                System.out.printf("Data final não pode ser menor que a data inicial!");
                            }
                        } else {
                            System.out.println("Data final inválida, informe no formato (dd/MM/yyyy)");
                        }
                    } else {
                        System.out.println("Data inicial inválida, informe no formato (dd/MM/yyyy)");
                    }
                } else {
                    System.out.printf("Descrição informada já existe no cadastro!");
                }
            } else {
                System.out.printf("Título informado já existe no cadastro!");
            }
        }

    }

    public boolean listaDeTarefas() {
        if (tarefaDao.listaTarefa.isEmpty()) {
            return true;
        } else {
            for (Tarefa tarefa : tarefaDao.listaTarefa) {
                System.out.printf("\n\nId: " + tarefa.id + "\nTarefa: " + tarefa.titulo + "\nDescrição: " + tarefa.descricao + "\nData inicial: " + tarefa.dataInicial + "\nData final: " + tarefa.dataFinal);
            }
        }
        return false;
    }

    public void buscarTarefasPorId(String id) {
        for (Tarefa tarefa : tarefaDao.listaTarefa) {
            if (tarefa.getId().toString().equalsIgnoreCase(id))
                System.out.printf("\n\nId: " + tarefa.id + "\nTarefa: " + tarefa.titulo + "\nDescrição: " + tarefa.descricao + "\nData inicial: " + tarefa.dataInicial + "\nData final: " + tarefa.dataFinal + "\n\n");

        }

    }

    public void atualizarTarefa(UUID codigoId, String descricao) {

        if (existeId(codigoId))
            if (!existeDescricao(descricao))
                System.out.print(tarefaDao.findAll() + "\n");

        for (int i = 0; i < tarefaDao.listaTarefa.size(); i++) {
            if (tarefaDao.listaTarefa.get(i).getId().toString().equalsIgnoreCase(codigoId.toString())) {
                Tarefa tarefa1 = new Tarefa(tarefaDao.listaTarefa.get(i).getId(), tarefaDao.listaTarefa.get(i).getTitulo(), descricao, tarefaDao.listaTarefa.get(i).getDataInicial(), tarefaDao.listaTarefa.get(i).getDataFinal());
                tarefaDao.atualizar(i, tarefa1);
                System.out.print(tarefaDao.findAll() + "\n");
            }
        }
    }

    public boolean existeId(UUID id) {
        for (Tarefa tarefa : tarefaDao.listaTarefa) {
            if (tarefa.getId().toString().equalsIgnoreCase(id.toString())) {
                return true;
            }
        }
        return false;
    }

    public void excluirTarefaDoCadastrpo(UUID codigo) {
        for (int i = 0; i < tarefaDao.listaTarefa.size(); i++) {
            if (tarefaDao.listaTarefa.get(i).id.toString().equalsIgnoreCase(codigo.toString())) {
                tarefaDao.listaTarefa.remove(i);
                System.out.print(tarefaDao.findAll() + "\n");
            }
        }

    }

    public boolean existeTitulo(String titulo) {
        for (Tarefa tarefa : tarefaDao.listaTarefa) {
            if (tarefa.titulo.equalsIgnoreCase(titulo)) {

                return true;
            }
        }
        return false;
    }

    public boolean existeDescricao(String nome) {
        for (Tarefa tarefa : tarefaDao.listaTarefa) {
            if (tarefa.descricao.equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarData(String data) {

        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        formatoData.setLenient(false);
        try {
            formatoData.parse(data);
        } catch (ParseException parseException) {
            return false;

        }
        return true;
    }

    public boolean validarAno(Tarefa tarefa) {

        if (tarefa.getDataInicial().matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || tarefa.getDataFinal().matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
            return true;
        }
        System.out.println("Data inválida, informe no formato (dd/MM/yyyy)");
        return false;
    }

    public boolean dataFinalMenorQueDataInicial(Tarefa tarefa) {
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        formatoData.setLenient(false);
        try {
            Date dataInicialTeste = formatoData.parse(tarefa.getDataInicial());
            Date dataFinalTeste = formatoData.parse(tarefa.getDataFinal());
            boolean result = dataInicialTeste.after(dataFinalTeste);
            if (result) {
                return true;
            }
        } catch (ParseException parseException) {

        }

        return false;
    }

}