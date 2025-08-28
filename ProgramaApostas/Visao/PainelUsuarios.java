package Visao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.Usuario;
import Persistencia.BancoDeDados;
import Persistencia.IDInvalido;
import Persistencia.Persistente;

public class PainelUsuarios extends JPanel implements ActionListener{
    
    private Font FpadraoBot = new Font("Arial", Font.PLAIN, 15);
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JButton botCriarUsuario,botVoltar,botSelUsuario,botApagarUsuario,botAlterarUsuario,botBuscarUsuario;

    private BancoDeDados bancoDeDadosPU;
    private CardLayout cardLayout;
    private JPanel painelPrincipalCardLayout;

    

    public PainelUsuarios(ActionListener listenerJanelaMain,BancoDeDados bancoDeDados){
        super(new BorderLayout());
        this.bancoDeDadosPU = bancoDeDados;
        JPanel jpPainelUsuario = new JPanel(new BorderLayout());

        // CardLayout entre PainelUsuarios e painelDosuarios
        cardLayout = new CardLayout();
        painelPrincipalCardLayout = new JPanel(cardLayout);
        painelPrincipalCardLayout.add(jpPainelUsuario,"PainelUsuarios");
        
        // Titulo 
        JLabel Titulo = new JLabel("Menu de usuario",SwingConstants.CENTER);
        Titulo.setFont(new Font("Arial", Font.BOLD, 45));
        jpPainelUsuario.add(Titulo,BorderLayout.NORTH);

        // Paineis
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel painelCentro = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    

        // Botões 
        botVoltar = new JButton("VOLTAR");
        botVoltar.setFont(new Font("Arial", Font.PLAIN, 20));
        botVoltar.setPreferredSize(new Dimension(200,40));
        botVoltar.setActionCommand("PainelMain");

        botSelUsuario = new JButton("Selecionar Usuario");
        botSelUsuario.setFont(FpadraoBot);
        botSelUsuario.setActionCommand("PainelDoUsuario");

        botCriarUsuario = new JButton("Criar Usuario");
        botCriarUsuario.setFont(FpadraoBot);
        botApagarUsuario = new JButton("Apagar Usuario");
        botApagarUsuario.setFont(FpadraoBot);
        botAlterarUsuario = new JButton("Alterar Usuario");
        botAlterarUsuario.setFont(FpadraoBot);
        botBuscarUsuario = new JButton("Busca de Usuario");
        botBuscarUsuario.setFont(FpadraoBot);
        

        // Adicoes Actionlistener
        botVoltar.addActionListener(listenerJanelaMain);
        botCriarUsuario.addActionListener(new CriarUsuario());
        botAlterarUsuario.addActionListener(new AlterarUsuario());
        botApagarUsuario.addActionListener(this);
        botBuscarUsuario.addActionListener(this);
        
        //Criacao unica para cada usuario
        botSelUsuario.addActionListener(new ActionListener()    {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario auxUsuario;
                Persistente<Usuario> auxUsuarios = bancoDeDadosPU.getUsuarios();

                try{
                    int auxID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                    auxUsuario = auxUsuarios.procurar(auxID);
                    PainelDoUsuario painelDoUsuario = new PainelDoUsuario(this,auxUsuario,bancoDeDadosPU.getEventos());
                    painelPrincipalCardLayout.add(painelDoUsuario,"PainelDoUsuario");
                    String comando = e.getActionCommand();
                    cardLayout.show(painelPrincipalCardLayout, comando);
                    atualizaTabela();
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
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Saldo");

        tabela = new JTable(modeloTabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(130);
        tabela.getColumnModel().getColumn(1).setMaxWidth(400);
        tabela.getColumnModel().getColumn(2).setMaxWidth(270);

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
        painelCentro.add(botSelUsuario, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botCriarUsuario, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botApagarUsuario, gbc);

        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botAlterarUsuario, gbc);

        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        painelCentro.add(botBuscarUsuario, gbc);

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
        jpPainelUsuario.add(painelCentro,BorderLayout.CENTER);
        jpPainelUsuario.add(painelVoltar,BorderLayout.SOUTH);
        add(painelPrincipalCardLayout,BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == botApagarUsuario){
            //Remove do banco de dados e da Tabela
            Persistente<Usuario> auxUsuarios = bancoDeDadosPU.getUsuarios();
            try{
                int auxID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                auxUsuarios.remover(auxID);
                modeloTabela.removeRow(tabela.getSelectedRow());
                tabela.clearSelection();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Erro: Nenhuma linha escolhida", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == botBuscarUsuario){

            String aux = JOptionPane.showInputDialog("Digite ID");
            if(aux == null){return;}
            Persistente<Usuario> auxUsuarios = bancoDeDadosPU.getUsuarios();
            try{
                int auxID = Integer.parseInt(aux);
                auxUsuarios.procurar(auxID);
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

    class CriarUsuario extends JFrame implements ActionListener{

        JTextField textField0;

        public CriarUsuario(){
            super("Criação de Usuario");
            setSize(600,150);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            JPanel painel0 = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JLabel text0 = new JLabel("Criação do Usuario",SwingConstants.CENTER);
            text0.setFont(new Font("Arial", Font.BOLD, 25));
            JLabel text1 = new JLabel("Digite o Nome:");
            text1.setFont(new Font("Arial", Font.BOLD, 20));
            textField0 =  new JTextField();
            textField0.setFont(new Font("Arial", Font.PLAIN, 20));
            textField0.setPreferredSize(new Dimension(300, 30));
            textField0.addActionListener(this);
            JLabel textEnter = new JLabel("Enter para confirmar",SwingConstants.CENTER);
            textEnter.setFont(new Font("Arial", Font.BOLD, 10));

            add(text0,BorderLayout.NORTH);
            painel0.add(text1);
            painel0.add(textField0);
            painel0.add(textEnter);
            add(painel0,BorderLayout.CENTER);
           
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botCriarUsuario){
                setVisible(true);
            }
            else if(e.getSource() == textField0){
                //Criar no banco de dados e depois colocar
                Usuario novoUsuario = new Usuario(textField0.getText());
                Persistente<Usuario> auxUsuarios = bancoDeDadosPU.getUsuarios();
                auxUsuarios.inserção(novoUsuario);
                modeloTabela.addRow(new Object[]{
                    novoUsuario.getID(),
                    novoUsuario.getNome(),
                    novoUsuario.VerSaldo()
                });
                textField0.setText("");
            }
        }
    }

    class AlterarUsuario extends JFrame implements ActionListener{

        private JTextField textFieldAlt0;

        public AlterarUsuario(){
            setSize(600,150);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            JPanel painelAlt = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JLabel textAlt0 = new JLabel("Alteração do Usuario",SwingConstants.CENTER);
            textAlt0.setFont(new Font("Arial", Font.BOLD, 25));
            JLabel textAlt1 = new JLabel("Digite o Nome:");
            textAlt1.setFont(new Font("Arial", Font.BOLD, 20));
            textFieldAlt0 =  new JTextField();
            textFieldAlt0.setFont(new Font("Arial", Font.PLAIN, 20));
            textFieldAlt0.setPreferredSize(new Dimension(300, 30));
            textFieldAlt0.addActionListener(this);
            JLabel textEnter = new JLabel("Enter para confirmar",SwingConstants.CENTER);
            textEnter.setFont(new Font("Arial", Font.BOLD, 10));

            add(textAlt0,BorderLayout.NORTH);
            painelAlt.add(textAlt1);
            painelAlt.add(textFieldAlt0);
            painelAlt.add(textEnter);
            add(painelAlt,BorderLayout.CENTER);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botAlterarUsuario){
                setVisible(true);
            }
            else if(e.getSource() == textFieldAlt0){
                Persistente<Usuario> auxUsuarios = bancoDeDadosPU.getUsuarios();
                
                try{
                    int altID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                    Usuario altUsuario = new Usuario(textFieldAlt0.getText(),altID);
                    auxUsuarios.alteracao(altID, altUsuario);
                    modeloTabela.setValueAt(altUsuario.getID(), tabela.getSelectedRow(), 0);
                    modeloTabela.setValueAt(altUsuario.getNome(), tabela.getSelectedRow(), 1);
                    modeloTabela.setValueAt(altUsuario.VerSaldo(), tabela.getSelectedRow(), 2);
                }catch(IDInvalido idE){
                    JOptionPane.showMessageDialog(this, idE, "Erro de ID invalido",JOptionPane.ERROR_MESSAGE);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Erro: Nenhuma linha escolhida", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                }
                tabela.clearSelection();
                textFieldAlt0.setText("");
            }
        }
        
    }

    public void atualizaTabela(){
        Persistente<Usuario> auxUsuario = bancoDeDadosPU.getUsuarios();
        Usuario temp;
        int auxID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
        try {
            temp = auxUsuario.procurar(auxID);
            modeloTabela.setValueAt(temp.VerSaldo(), tabela.getSelectedRow(), 2);
        } catch (IDInvalido e) {
            return;
        }
    }
}