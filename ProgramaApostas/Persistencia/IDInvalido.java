package Persistencia;

public class IDInvalido extends Exception{
    public IDInvalido(){
        super("--- Erro: ID Inexistente");
    }
    
    public String toString(){
        return "Erro: ID Inexistente";
    }
}
