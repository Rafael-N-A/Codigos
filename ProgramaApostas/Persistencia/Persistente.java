package Persistencia;

import java.util.ArrayList;

import Modelo.Entidade;

public class Persistente <T extends Entidade> {
    
    ArrayList<T> entidades;

    public Persistente(){
        entidades = new ArrayList<T>();
    }

    public void inserção(T inserir){
        entidades.add(inserir);
    }

    public void remover(int ID) throws IDInvalido {
        T aux = procurar(ID);
        int indiceR = entidades.indexOf(aux);
        if(indiceR == -1){
            return;
        }
        entidades.remove(indiceR);
    }

    public void alteracao(int ID,T alterar) throws IDInvalido  {
        T aux = procurar(ID);
        ID = entidades.indexOf(aux);
        if(ID == -1){
            return;
        }
        entidades.set(ID, alterar);
    }

    public T procurar (int ID) throws IDInvalido {//Procura por ID, Retorna, null se não existir
        
        for(int i = 0;i<entidades.size();i++){
            T aux = entidades.get(i);
            if(aux.getID() == ID){
                return aux;
            }
        }
        throw new IDInvalido();
    }

    public String toString(){
        String aux = "";
        for(int i = 0;i<entidades.size();i++){
            T temp = entidades.get(i);
            aux += temp.toString();
        }
        return aux;
    }

}
