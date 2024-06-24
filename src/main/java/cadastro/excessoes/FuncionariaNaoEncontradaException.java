package cadastro.excessoes;

public class FuncionariaNaoEncontradaException extends Exception {
    public FuncionariaNaoEncontradaException(String message){
        super(message);
    }
}
