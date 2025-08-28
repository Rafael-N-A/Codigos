package Visao;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class PainelMain extends JPanel{
    
    public PainelMain(ActionListener listenerJanelaMain){
        super(new GridLayout(2, 0));
        
        //Painel Principal da Janela
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal,BoxLayout.Y_AXIS));
        
        //Titulo

        JLabel Titulo = new JLabel("Site de Apostas", SwingConstants.CENTER);
        Titulo.setFont(new Font("Arial", Font.BOLD, 50));
        add(Titulo);

        //Botões

        JButton botUsuarios = new JButton("Menu dos Usuarios");
        botUsuarios.setPreferredSize(new Dimension(600, 50));
        botUsuarios.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        botUsuarios.setFont(new Font("Arial", Font.PLAIN, 30));
        botUsuarios.setActionCommand("PainelUsuarios");
        JButton botEventos = new JButton("Menu dos Eventos");
        botEventos.setPreferredSize(new Dimension(600, 50));
        botEventos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        botEventos.setFont(new Font("Arial", Font.PLAIN, 30));
        botEventos.setActionCommand("PainelEventos");

        // Adicoes ActionListener
        botUsuarios.addActionListener(listenerJanelaMain);
        botEventos.addActionListener(listenerJanelaMain);

        //Painel dos botoes
        JPanel painelDosBot = new JPanel(null);
        painelDosBot.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        painelDosBot.add(botUsuarios);
        painelDosBot.add(botEventos);
        painelPrincipal.add(painelDosBot);

        add(painelPrincipal);
    }
}
