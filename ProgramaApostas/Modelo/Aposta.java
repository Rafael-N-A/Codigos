package Modelo;

public class Aposta extends Entidade{

    private Usuario usuario;
    private Evento evento;
    private OpcaoAposta opcaoAposta;
    private double valorApostado;
    private double valorPontecial;
    private boolean status,resultado;

    public Aposta(Usuario usuario,Evento evento,OpcaoAposta opcaoAposta,Double valorApostado){
        this.usuario = usuario;
        this.evento = evento;
        this.opcaoAposta = opcaoAposta;
        this.valorApostado = valorApostado;
        this.valorPontecial = opcaoAposta.getOdd() * valorApostado;
        this.status = false;
    }

    public String getEvento(){
        return evento.DescricaoEvento();
    }

    public String getNomeAposta(){
        return opcaoAposta.getNomeOpAposta();
    }

    public boolean getStatus(){//False = Aberta | True = Fechada
        return status;
    }

    public void atualizarAposta()//Atualiza somente quando o jogo e a aposta são fechadas e fecha a aposta
    {
        if(!evento.getStatus() && opcaoAposta.getStatus()){
            this.status = true;
            this.resultado = opcaoAposta.getResultado();
            if(resultado){
            valorPontecial = opcaoAposta.getOdd() * valorApostado;
            usuario.Depositar(valorPontecial);
            }
        }
    }

    public double getValorPotencial(){
        return valorPontecial;
    }

    public double getValorApostado() {
        return valorApostado;
    }

    public String verResultado(){//Status true = Jogo fechado | False = Aberto
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

    @Override
    public String toString() {
        return "\nID: " + getID() + "\nClasse: Aposta" +
        "\nUsuario: " + usuario.getNome() + "\nEvento: " + evento.DescricaoEvento()
        + "\nAposta: " + opcaoAposta.getNomeOpAposta() + "\nValor Apostado: " + getValorApostado()
        + "\nValor Potencial: " + valorPontecial + "\nStatus: " + verResultado()
        + "\n-------------";
    }
}
