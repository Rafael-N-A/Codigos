package Visao;
import javax.swing.*;

import Persistencia.BancoDeDados;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal extends JFrame implements ActionListener{
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public JanelaPrincipal(BancoDeDados bancoDeDados){
        super("Site de Apostas");

        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        //Adiciona os paineis principais no cardlayout
        painelPrincipal.add(new PainelMain(this), "PainelMain");
        //painelPrincipal.add(new PainelDoEvento(this),"PainelDoEvento");
        painelPrincipal.add(new PainelUsuarios(this,bancoDeDados), "PainelUsuarios");
        painelPrincipal.add(new PainelEventos(this,bancoDeDados.getEventos()), "PainelEventos");

        add(painelPrincipal,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        cardLayout.show(painelPrincipal, comando);
    }
}
