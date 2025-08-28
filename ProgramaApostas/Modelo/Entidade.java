package Modelo;

public abstract class Entidade {
    static protected int qtdID = 0;
    protected int ID = 0;

    public Entidade(){
        ID = qtdID;
        qtdID++;
    }

    public int getID(){
        return ID;
    }

    public abstract String toString();
}
