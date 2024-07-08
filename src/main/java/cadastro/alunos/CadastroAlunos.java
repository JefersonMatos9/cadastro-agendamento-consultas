package cadastro.alunos;

import cadastro.cadastro.Cadastro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CadastroAlunos {
    private List<Alunos> listaAlunos;

    public CadastroAlunos(){
        listaAlunos = new ArrayList<>();
    }
    // Metodo de Cadastrar Alunos
    public void cadastrarAluno( String nome, LocalDate dataNasc, String rua, String bairro, String cidade, String estado) throws Exception {
        // Foreach para verificar se o nome do aluno ja consta na lista.
        for (Alunos aluno : listaAlunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                // Caso o nome do aluno conste na lista ele lança uma execessão .
                throw new Exception("Aluno ja possui cadastro.");
            }
        }
        //Iniciando o cadastro se o aluno não estiver na lista.
        Alunos novoAluno = new Alunos();
        novoAluno.setNome(nome);
        novoAluno.setDataNasc(dataNasc);
        novoAluno.setRua(rua);
        novoAluno.setBairro(bairro);
        novoAluno.setCidade(cidade);
        novoAluno.setEstado(estado);
        //Adicionando aluno a lista
        listaAlunos.add(novoAluno);
    }
    public Cadastro buscarAluno(String nome) throws Exception {
        if (listaAlunos.isEmpty()) {
            throw new Exception("A lista está vazia.");
        }
        for (Cadastro aluno : listaAlunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                return aluno;
            }
        }
        throw new Exception("O nome consultado não existe em nosso Banco de Dados !");
    }
}

