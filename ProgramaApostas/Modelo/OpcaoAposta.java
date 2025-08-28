package Modelo;

public class OpcaoAposta extends Entidade{

    private String nomeAposta;
    private double odd;
    private boolean status,resultado;

    public OpcaoAposta(String nomeAposta){
        this.nomeAposta = nomeAposta;
        this.odd = 1; 
        status = false;
    }

    public void AtualizarOdd(double odd) // Odd em decimal e >= 1
    {
        this.odd = odd;
    }

    public void setStatus(boolean status){//Falso = Aberto
        this.status = status;
    }

    public void definirResultado(boolean resultado){//Define o resultado e encerra a aposta
        this.status = true;
        this.resultado = resultado;
    }

    public boolean getResultado(){
        return resultado;
    }

    public boolean getStatus(){
        return status;
    }

    public String verResultado(){/*Status True = Fechada | False = Aberta
    Resultado True = Ganhou | False = Perdeu */
        if(status){
            if(resultado){
                return "Vencedora";
            }else{
                return "Perdedora";
            }
        }else{
            return "Aberta";
        }
    }

    public double getOdd() {
        return odd;
    }

    public String getNomeOpAposta() {
        return nomeAposta;
    }

    @Override
    public String toString() {
        return "\nDescrição: " + getNomeOpAposta() + " Odd: " + odd
        + "\n-------------";
    }
}
