package Modelo;

import java.util.ArrayList;

public class Evento extends Entidade{

    private String timeA,timeB;
    private int resultado;
    private boolean status;
    private ArrayList<OpcaoAposta> opcoesAposta;//Apostas do Evento

    public Evento(String timeA,String timeB){
        opcoesAposta = new ArrayList<OpcaoAposta>();
        this.timeA = timeA;
        this.timeB = timeB;
        status = true;
        resultado = 0;
    }

    public Evento(String timeA,String timeB,int iD){
        Entidade.qtdID = qtdID - 1;
        opcoesAposta = new ArrayList<OpcaoAposta>();
        this.timeA = timeA;
        this.timeB = timeB;
        status = true;
        resultado = 0;
        this.ID = iD;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public String getTimeA() {
        return timeA;
    }
    
    public String getTimeB() {
        return timeB;
    }


    public OpcaoAposta getOpcoesAposta(int indice) {
        return opcoesAposta.get(indice);
    }

    public void inserirAposta(OpcaoAposta opAposta){
        opcoesAposta.add(opAposta);
    }

    public void removerAposta(int indice){
        opcoesAposta.remove(indice);
    }

    public boolean existeAposta()// False se não existe, True se existe
    {
        if(0 == opcoesAposta.size()){
            return false;
        }else{
            return true;
        }
    }

    public int getTamanhoMercadoAp(){
        return opcoesAposta.size();
    }

    public String verApostas(){//Print das Opções de Aposta vinculada a array
        String aux = "";
        for(int i = 0;i<opcoesAposta.size();i++){
            OpcaoAposta auxOP = opcoesAposta.get(i);
            aux += ("Aposta Indice: " + i + 
            "\nAposta: " + auxOP.getNomeOpAposta() + " Odd: " + auxOP.getOdd()
            + "\n-------------\n");
        }
        return aux;
    }

    public String VerStatus() { // True = em jogo | False = encerrado
        if(status){
            return "Em jogo";
        }else{
            return "Encerrado";
        }
    }
    
    public String DescricaoEvento() {
        return timeA + " VS " + timeB;
    }

    public void setResultado(int resultado)//Fecha o jogo e atualiza o resultado
    {
        status = false;
        this.resultado = resultado;
    }

    public String getResultado() {// 3 opções para resultado VtimeA/VtimeB/Emp
        if(resultado == 0){
            return "Aberto";
        }else if(resultado == 1){
            return "Vitoria de " + timeA;
        }else if(resultado == 2){
            return "Vitoria de " + timeB;
        }else{
            return "Empate";
        }
    }

    public String toString(){
        return "ID:" + getID() + 
        "\nEvento: " + DescricaoEvento() + "\nStatus: " + VerStatus() 
        + " | Resultado: " + getResultado()
        + "\n-------------\n";
    }
}
