# Cadastro e Agendamento de Consultas

## Descrição

Este projeto é uma aplicação de cadastro e agendamento de consultas para alunos e funcionários. Ele permite cadastrar alunos e funcionários, agendar horários de consulta e calcular o total a pagar para os alunos e o total a receber para os funcionários.

## Funcionalidades

- **Cadastro de Alunos**: Permite cadastrar novos alunos com informações pessoais e detalhes do agendamento.
- **Cadastro de Funcionários**: Permite cadastrar novos funcionários com informações pessoais e detalhes de trabalho.
- **Agendamento de Horários**: Permite agendar horários para alunos com funcionários específicos.
- **Cálculo de Total a Pagar**: Calcula o total a pagar para os alunos com base nas sessões agendadas e no preço por hora.
- **Cálculo de Total a Receber**: Calcula o total a receber para os funcionários com base nas horas trabalhadas e no salário.

## Estrutura do Projeto

- `agendamento/`: Contém classes relacionadas ao agendamento de horários.
- `cadastro/alunos/`: Contém classes para cadastro e gerenciamento de alunos.
- `cadastro/cadastroFuncionarios/`: Contém classes para cadastro e gerenciamento de funcionários.
- `cadastro/excessoes/`: Contém classes de exceções personalizadas.
- `classePrincipal/`: Contém a classe principal que executa a aplicação.

## Diagrama de Classes

```mermaid
classDiagram
    class CadastroAlunos {
        - List~Alunos~ listaAlunos
        + cadastrarAluno(nome: String, dataNasc: LocalDate, rua: String, bairro: String, cidade: String, estado: String, quantidadeSessoes: int, precoPorHora: double, totalAPagar: double)
        + buscarAluno(nome: String) : Alunos
    }
    
    class CadastroFuncionarias {
        - List~Funcionarias~ listaFuncionarias
        + cadastrarFuncionaria(nome: String, cpf: String, dataNasc: LocalDate, rua: String, bairro: String, cidade: String, estado: String, funcao: String, horaTrabalhada: int, salario: double, totalAReceber: double)
        + excluirFuncionaria(nome: String)
        + pesquisarFuncionaria(nome: String) : Funcionarias
    }
    
    class AgendarHorario {
        - List~Funcionarias~ funcionarias
        - List~Alunos~ alunos
        + adicionarFuncionaria(funcionaria: Funcionarias)
        + adicionarAluno(aluno: Alunos)
        + agendarHorario(alunoNome: String, funcionariaNome: String, data: LocalDate, hora: String) : String
    }
    
    class Alunos {
        + String nome
        + LocalDate dataNasc
        + String rua
        + String bairro
        + String cidade
        + String estado
        + int quantidadeSessoes
        + double precoPorHora
        + double totalAPagar
        + getNome() : String
        + setNome(nome: String)
        + getTotalAPagar() : double
        + setTotalAPagar(totalAPagar: double)
    }
    
    class Funcionarias {
        + String nome
        + String cpf
        + LocalDate dataNasc
        + String rua
        + String bairro
        + String cidade
        + String estado
        + String funcao
        + int horaTrabalhada
        + double salario
        + double totalAReceber
        + getNome() : String
        + setNome(nome: String)
        + getTotalAReceber() : double
        + setTotalAReceber(totalAReceber: double)
    }

    CadastroAlunos "1" *-- "N" Alunos
    CadastroFuncionarias "1" *-- "N" Funcionarias
    AgendarHorario "1" *-- "N" Alunos
    AgendarHorario "1" *-- "N" Funcionarias
