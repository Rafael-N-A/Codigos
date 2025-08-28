package Visao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.Evento;
import Persistencia.IDInvalido;
import Persistencia.Persistente;

public class PainelEventos extends JPanel implements ActionListener{
    
    private Font FpadraoBot = new Font("Arial", Font.PLAIN, 15);
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JButton botCriarEvento,botSelEvento,botApagarEvento,botAlterarEvento,botBuscarEvento;

    private CardLayout cardLayout;
    private JPanel painelPrincipalCardLayout;
    private Persistente<Evento> auxEventos;

    public PainelEventos(ActionListener listenerJanelaMain,Persistente<Evento> evento){
        super(new BorderLayout());
        auxEventos = evento;

        //Painel para o PainelEventos
        JPanel jpPainelEventos = new JPanel(new BorderLayout());

        // CardLayout entre PainelEventos e painelDoEvento
        cardLayout = new CardLayout();
        painelPrincipalCardLayout = new JPanel(cardLayout);
        painelPrincipalCardLayout.add(jpPainelEventos,"PainelEventos");

        // Titulo 
        JLabel Titulo = new JLabel("Menu de Eventos",SwingConstants.CENTER);
        Titulo.setFont(new Font("Arial", Font.BOLD, 45));
        jpPainelEventos.add(Titulo,BorderLayout.NORTH);

        // Paineis
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel painelCentro = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    

        // Botões 
        JButton botVoltar = new JButton("VOLTAR");
        botVoltar.setFont(new Font("Arial", Font.PLAIN, 20));
        botVoltar.setPreferredSize(new Dimension(200,40));
        botVoltar.setActionCommand("PainelMain");
        botSelEvento = new JButton("Selecionar Evento");
        botSelEvento.setFont(FpadraoBot);
        botSelEvento.setActionCommand("PainelDoEvento");
        botCriarEvento = new JButton("Criar Evento");
        botCriarEvento.setFont(FpadraoBot);
        botApagarEvento = new JButton("Apagar Evento");
        botApagarEvento.setFont(FpadraoBot);
        botAlterarEvento = new JButton("Alterar Evento");
        botAlterarEvento.setFont(FpadraoBot);
        botBuscarEvento = new JButton("Buscar Evento");
        botBuscarEvento.setFont(FpadraoBot);

        // Adicoes Actionlistener
        botVoltar.addActionListener(listenerJanelaMain);
        botCriarEvento.addActionListener(new CriarEvento());
        botApagarEvento.addActionListener(this);
        botAlterarEvento.addActionListener(new AlterarEvento());
        botBuscarEvento.addActionListener(this);

        //Criacao unica para cada evento
        botSelEvento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Evento auxEvento;
                try{
                    int auxID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                    auxEvento = auxEventos.procurar(auxID);
                    PainelDoEvento painelDoEvento = new PainelDoEvento(this,auxEvento);
                    painelPrincipalCardLayout.add(painelDoEvento,"PainelDoEvento");
                    String comando = e.getActionCommand();
                    cardLayout.show(painelPrincipalCardLayout, comando);

                    //Atualização da Tabela
                    modeloTabela.setValueAt(auxEvento.VerStatus(), tabela.getSelectedRow(), 2);
                    modeloTabela.setValueAt(auxEvento.getResultado(), tabela.getSelectedRow(), 3);
                }catch(IDInvalido idE){
                    JOptionPane.showMessageDialog(new JFrame(), idE, "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(), "Erro: Nenhuma linha escolhida", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                }
            }  
        });

        //JTable
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Evento");
        modeloTabela.addColumn("Status");
        modeloTabela.addColumn("Resultado");


        tabela = new JTable(modeloTabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela.getColumnModel().getColumn(1).setMaxWidth(350);
        tabela.getColumnModel().getColumn(2).setMaxWidth(180);
        tabela.getColumnModel().getColumn(3).setMaxWidth(180);

        var scrool = new JScrollPane();
        scrool.setViewportView(tabela);
        
        // Adicoes no gridBag
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //linha 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botSelEvento, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botCriarEvento, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botApagarEvento, gbc);

        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botAlterarEvento, gbc);

        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botBuscarEvento, gbc);

        //Linha da JTable
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        gbc.gridwidth = 10;
        gbc.insets = new Insets(5, 0, 5, 0);
        painelCentro.add(scrool,gbc);

        // Adicoes no painel
        painelVoltar.add(botVoltar);
        jpPainelEventos.add(painelCentro,BorderLayout.CENTER);
        jpPainelEventos.add(painelVoltar,BorderLayout.SOUTH);
        add(painelPrincipalCardLayout,BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    
        if(e.getSource() == botApagarEvento){
            //Remove do banco de dados e da Tabela
            try{
                int auxID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                auxEventos.remover(auxID);
                modeloTabela.removeRow(tabela.getSelectedRow());
                tabela.clearSelection();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Erro: Nenhuma linha escolhida", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == botBuscarEvento){
            String aux = JOptionPane.showInputDialog("Digite ID");
            try{
                int auxID = Integer.parseInt(aux);
                auxEventos.procurar(auxID);
                for(int i = 0;i < tabela.getRowCount();i++) {
                if(auxID == (int)tabela.getValueAt(i, 0)){
                    tabela.setRowSelectionInterval(i, i);
                }
            }
            }catch(IDInvalido idE){
                JOptionPane.showMessageDialog(this, idE, "Erro de ID invalido",JOptionPane.ERROR_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Apenas inteiros para o ID", "Erro de input",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class CriarEvento extends JFrame implements ActionListener{

        JTextField textField0,textField1;

        public CriarEvento(){
            super("Criação do Evento");
            setSize(500,200);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Criação do Evento",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);

            //Paineis
            JPanel gridLayout0 = new JPanel(new GridLayout(2, 0));
            JPanel painel0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel painel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

            //Componentes
            JLabel text0 = new JLabel("Time A:");
            text0.setFont(new Font("Arial", Font.BOLD, 20));
            JLabel text1 = new JLabel("Time B:");
            text1.setFont(new Font("Arial", Font.BOLD, 20));
            textField0 = new JTextField();
            textField0.setFont(new Font("Arial", Font.PLAIN, 20));
            textField0.setPreferredSize(new Dimension(300, 30));
            textField1 = new JTextField();
            textField1.setFont(new Font("Arial", Font.PLAIN, 20));
            textField1.setPreferredSize(new Dimension(300, 30));
            JLabel textEnter = new JLabel("Enter para confirmar",SwingConstants.CENTER);
            textEnter.setFont(new Font("Arial", Font.BOLD, 10));

            //Adicoes actionlistener
            textField0.addActionListener(this);
            textField1.addActionListener(this);

            //Adicoes
            painel0.add(text0);
            painel0.add(textField0);
            painel1.add(text1);
            painel1.add(textField1);
            painel1.add(textEnter);
            gridLayout0.add(painel0);
            gridLayout0.add(painel1);
            add(gridLayout0,BorderLayout.CENTER);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botCriarEvento){
                setVisible(true);
            }
            else if(e.getSource() == textField0 || e.getSource() == textField1){
                //Cria no banco de dados depois coloca
                Evento novoEvento = new Evento(textField0.getText(), textField1.getText());
                auxEventos.inserção(novoEvento);
                modeloTabela.addRow(new Object[]{
                    novoEvento.getID(),
                    novoEvento.DescricaoEvento(),
                    novoEvento.VerStatus(),
                    novoEvento.getResultado()
                });
                textField0.setText("");
                textField1.setText("");
            }
        }
    }

    class AlterarEvento extends JFrame implements ActionListener{

        JTextField textField0,textField1;

        public AlterarEvento(){
            super("Alteração de Evento");
            setSize(500,200);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Alteração de Evento",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);

            //Paineis
            JPanel gridLayout0 = new JPanel(new GridLayout(2, 0));
            JPanel painel0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel painel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

            //Componentes
            JLabel text0 = new JLabel("Time A:");
            text0.setFont(new Font("Arial", Font.BOLD, 20));
            JLabel text1 = new JLabel("Time B:");
            text1.setFont(new Font("Arial", Font.BOLD, 20));
            textField0 = new JTextField();
            textField0.setFont(new Font("Arial", Font.PLAIN, 20));
            textField0.setPreferredSize(new Dimension(300, 30));
            textField1 = new JTextField();
            textField1.setFont(new Font("Arial", Font.PLAIN, 20));
            textField1.setPreferredSize(new Dimension(300, 30));
            JLabel textEnter = new JLabel("Enter para confirmar",SwingConstants.CENTER);
            textEnter.setFont(new Font("Arial", Font.BOLD, 10));

            //Adicoes actionlistener
            textField0.addActionListener(this);
            textField1.addActionListener(this);

            //Adicoes
            painel0.add(text0);
            painel0.add(textField0);
            painel1.add(text1);
            painel1.add(textField1);
            painel1.add(textEnter);
            gridLayout0.add(painel0);
            gridLayout0.add(painel1);
            add(gridLayout0,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botAlterarEvento){
                setVisible(true);
            }
            else if(e.getSource() == textField0 || e.getSource() == textField1){
                
                try{
                    
                    int altID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                    Evento altEvento = new Evento(textField0.getText(),textField1.getText(),altID);
                    auxEventos.alteracao(altID, altEvento);
                    modeloTabela.setValueAt(altEvento.getID(), tabela.getSelectedRow(), 0);
                    modeloTabela.setValueAt(altEvento.DescricaoEvento(), tabela.getSelectedRow(), 1);
                    modeloTabela.setValueAt(altEvento.VerStatus(), tabela.getSelectedRow(), 2);
                    modeloTabela.setValueAt(altEvento.getResultado(), tabela.getSelectedRow(), 3);
                }catch(IDInvalido idE){
                    JOptionPane.showMessageDialog(this, idE, "Erro de ID invalido",JOptionPane.ERROR_MESSAGE);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Erro: Nenhuma linha escolhida", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                }
                tabela.clearSelection();
                textField0.setText("");
                textField1.setText("");
            }
        }
    }

}