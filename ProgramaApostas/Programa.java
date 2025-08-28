import Persistencia.BancoDeDados;
import Visao.JanelaPrincipal;

public class Programa {
    public static void main(String[] args) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        JanelaPrincipal j = new JanelaPrincipal(bancoDeDados);
        j.setVisible(true);
    }
}
