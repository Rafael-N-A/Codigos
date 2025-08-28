package Persistencia;

import Modelo.Evento;
import Modelo.Usuario;

public class BancoDeDados {

    Persistente<Usuario> usuarios;
    Persistente<Evento> eventos;

    public BancoDeDados(){
        usuarios = new Persistente<Usuario>();
        eventos = new Persistente<Evento>();
    }

    public Persistente<Evento> getEventos() {
        return eventos;
        
    }

    public Persistente<Usuario> getUsuarios() {
        return usuarios;
    }
}
