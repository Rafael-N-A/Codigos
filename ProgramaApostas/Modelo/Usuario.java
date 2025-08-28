package Modelo;

import java.util.ArrayList;

public class Usuario extends Entidade{

    private String nome;
    private double saldo;
    private ArrayList<Aposta> historicoAposta;// Vetor para as apostas feitas

    public Usuario(String nome){
        historicoAposta = new ArrayList<Aposta>();
        this.nome = nome;
        saldo = 0;
    }

    public Usuario(String nome,int iD){
        Entidade.qtdID = qtdID - 1;
        historicoAposta = new ArrayList<Aposta>();
        this.nome = nome;
        saldo = 0;
        this.ID = iD;
    }

    public void atualizarApostas(){ //Chamada para atualizar a aposta para o usuario
        for(int i = 0;i<historicoAposta.size();i++){
            Aposta aux = historicoAposta.get(i);
            if(!aux.getStatus()){//Se o status está false(Aberta)
                aux.atualizarAposta();//Fecha a aposta nesse metodo
            }
        }
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void inserirAposta(Aposta a){
        historicoAposta.add(a);
    }

    public int getTamHistAposta(){
        return historicoAposta.size();
    }

    public Aposta getAposta(int indice){//Pega a aposta a partir do indice
        return historicoAposta.get(indice);
    }

    public void removerAposta(int ID){
        Aposta aux = null;
        for(int i = 0;i<historicoAposta.size();i++){//Procura a aposta por ID
            aux = historicoAposta.get(i);
            if(aux.getID() == ID){
                break;
            }
            aux = null;
        }
        if(aux == null){return;}//Para se não existir o ID
        if(!aux.getStatus()){//Se a aposta ainda estiver aberta(False) retorna o dinheiro
            saldo += aux.getValorApostado();
        }
        int indiceR = historicoAposta.indexOf(aux);
        historicoAposta.remove(indiceR);
    }

    public void Depositar(double saldo) {
        this.saldo += saldo;
    }

    public double Sacar(double saque){
        if (saldo >= saque) {
            saldo -= saque;
            return saque;
        }else{
            return 0;
        }
    }

    public double VerSaldo() {
        return saldo;
    }

    public String getNome() {
        return nome;
    }

    public String toString(){
        return "\nID:" + getID() 
        + "\nClasse: Usuario" + "\nNome: " + getNome() + "\nSaldo: R$" + VerSaldo()
        + "\n-------------\n";
    }
}
