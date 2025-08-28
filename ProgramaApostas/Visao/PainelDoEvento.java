package Visao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Modelo.Evento;
import Modelo.OpcaoAposta;

public class PainelDoEvento extends JPanel{

    private Font FpadraoBot = new Font("Arial", Font.PLAIN, 15);
    private Dimension dimensionPadraoBot = new Dimension(300,100);

    private JButton botVisMercadoApostas,botAtualizaResEvento;

    private JLabel status = new JLabel();
    private JLabel resultado = new JLabel();

    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private Evento evento;

    public PainelDoEvento(ActionListener listenerPainelEventos,Evento evento){
        super(new BorderLayout());
        this.evento = evento;

        //Pre-Criacao da JTable
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Odd");
        modeloTabela.addColumn("Resultado");

        tabela = new JTable(modeloTabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(400);
        tabela.getColumnModel().getColumn(1).setMaxWidth(100);
        tabela.getColumnModel().getColumn(2).setMaxWidth(200);

        inicializaTabela();

        //Titulo
        JLabel Titulo = new JLabel("Evento: " + evento.DescricaoEvento(),SwingConstants.CENTER);
        Titulo.setFont(new Font("Arial", Font.BOLD, 45));
        add(Titulo,BorderLayout.NORTH);

        //Painel e Botao voltar
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botVoltar = new JButton("VOLTAR");
        botVoltar.setFont(new Font("Arial", Font.PLAIN, 20));
        botVoltar.setPreferredSize(new Dimension(200,40));
        botVoltar.setActionCommand("PainelEventos");
        botVoltar.addActionListener(listenerPainelEventos);
        painelVoltar.add(botVoltar);

        // Botoes de acao

        botVisMercadoApostas = new JButton("Visualizar Mercado De Apostas");
        botVisMercadoApostas.setFont(FpadraoBot);
        botVisMercadoApostas.setPreferredSize(dimensionPadraoBot);
        botAtualizaResEvento = new JButton("Atualizar Resultado Do Evento");
        botAtualizaResEvento.setFont(FpadraoBot);
        botAtualizaResEvento.setPreferredSize(dimensionPadraoBot);

        //Actionlistener dos botoes
        botVisMercadoApostas.addActionListener(new VisualizarMercadoAposta());
        botAtualizaResEvento.addActionListener(new AtualizarResEvento());

        // Borda
        Border externa = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border interna = BorderFactory.createEmptyBorder(3, 3, 3, 3);

        // Detalhes do Evento
        status.setText("Status: " + evento.VerStatus());
        status.setOpaque(true);
        status.setForeground(Color.BLACK);
        status.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
        status.setBorder(BorderFactory.createCompoundBorder(externa, interna));
        status.setBackground(Color.GREEN);

        resultado.setText("Resultado: " + evento.getResultado());
        resultado.setOpaque(true);
        resultado.setForeground(Color.BLACK);
        resultado.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
        resultado.setBorder(BorderFactory.createCompoundBorder(externa, interna));
        resultado.setBackground(Color.GRAY);



        //Painel Central - Gridbag
        JPanel painelCentral = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 20, 60, 20);

        // Linha 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelCentral.add(status,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelCentral.add(resultado,gbc);

        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.BOTH;
        //linha 1
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        painelCentral.add(botVisMercadoApostas,gbc);

        //linha 2
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        painelCentral.add(botAtualizaResEvento,gbc);

        //Adicoes no painel
        add(painelVoltar,BorderLayout.SOUTH);
        add(painelCentral,BorderLayout.CENTER);
    }

    class VisualizarMercadoAposta extends JFrame implements ActionListener{

        JButton botAtualizarAposta,botCriarAposta,botAttResultado;

        public VisualizarMercadoAposta(){
            super("Mercado De Aposta");
            setSize(600,500);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Mercado de Aposta",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);
            
            //Botoes de acao
            botAtualizarAposta = new JButton("Atualizar Odd Aposta");
            botAtualizarAposta.setFont(FpadraoBot);
            botCriarAposta = new JButton("Criar Aposta");
            botCriarAposta.setFont(FpadraoBot);
            botAttResultado = new JButton("Atualizar Resultado");
            botAttResultado.setFont(FpadraoBot);

            //Adicoes ActionListener
            botCriarAposta.addActionListener(new CriarAposta(botCriarAposta));

            //Actionlisterner
            botAttResultado.addActionListener(new AtualizarResAposta(botAttResultado));

            //Actionlistener
            botAtualizarAposta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String oddAtt = JOptionPane.showInputDialog(new JFrame(),"Escreva o Valor","Atualizar a ODD",JOptionPane.PLAIN_MESSAGE);
                    if(oddAtt == null){return;}
                    try {
                        double odd = Double.parseDouble(oddAtt);
                        OpcaoAposta auxOA = evento.getOpcoesAposta(tabela.getSelectedRow());
                        auxOA.AtualizarOdd(odd);
                        atualizaTabelaMercadoAp();
                    } catch (NumberFormatException numE) {
                        JOptionPane.showMessageDialog(new JFrame(), "Apenas decimais para Odd", "Erro de input",JOptionPane.ERROR_MESSAGE);
                    }catch(IndexOutOfBoundsException indE){
                        JOptionPane.showMessageDialog(new JFrame(), "Indice invalido. Selecione uma linha", "Erro de input",JOptionPane.ERROR_MESSAGE);
                    } catch (Exception erro){
                        JOptionPane.showMessageDialog(new JFrame(), "Digite um valor", "Erro de input",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            //Painel gridBag
            JPanel painelCentro = new JPanel(new GridBagLayout());
            var gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.insets = new Insets(20, 3, 10, 3);
            //linha 0
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridwidth = 2;
            painelCentro.add(botCriarAposta,gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridwidth = 2;
            painelCentro.add(botAtualizarAposta,gbc);

            gbc.gridx = 4;
            gbc.gridy = 0;
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridwidth = 2;
            painelCentro.add(botAttResultado,gbc);

            gbc.insets = new Insets(0, 5, 5, 5);
            //linha Jtable
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridwidth = 6;

            var scrool = new JScrollPane();
            scrool.setViewportView(tabela);

            painelCentro.add(scrool,gbc);

            add(painelCentro,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botVisMercadoApostas){
                setVisible(true);
            }
            
        }
    }

    public class CriarAposta extends JFrame implements ActionListener{

        JTextField textFieldNomeOP,textFieldOdd;
        JButton botIniciar;

        public CriarAposta(JButton botAcao){
            super("Criação de Aposta");
            botIniciar = botAcao;
            setSize(540,200);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Criar Aposta",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);

            JPanel painelPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT));

            textFieldNomeOP = new JTextField(10);
            textFieldNomeOP.setFont(new Font("Arial", Font.PLAIN, 20));
            textFieldNomeOP.addActionListener(this);
            JLabel textNomeOp = new JLabel("Digite o nome da Aposta: ");
            textNomeOp.setFont(new Font("Arial", Font.PLAIN, 20));

            textFieldOdd = new JTextField(10);
            textFieldOdd.setFont(new Font("Arial", Font.PLAIN, 20));
            textFieldOdd.addActionListener(this);
            JLabel textOdd = new JLabel("Digite a ODD: ");
            textOdd.setFont(new Font("Arial", Font.PLAIN, 20));
            textOdd.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 117));
            JLabel textEnter = new JLabel("Enter para confirmar");
            textEnter.setFont(new Font("Arial", Font.BOLD, 10));

            painelPrincipal.add(textNomeOp);
            painelPrincipal.add(textFieldNomeOP);
            painelPrincipal.add(textOdd);
            painelPrincipal.add(textFieldOdd);
            painelPrincipal.add(textEnter);
            
            add(painelPrincipal);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botIniciar) {
                setVisible(true);
            }else if(textFieldNomeOP.getText() == ""){
                JOptionPane.showMessageDialog(this, "Nome da Aposta Vazia", "Erro de Input",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                String nomeAposta = textFieldNomeOP.getText();
                try {
                    String auxOdd = textFieldOdd.getText();
                    double odd = Double.parseDouble(auxOdd);
                    OpcaoAposta novaOP = new OpcaoAposta(nomeAposta);
                    novaOP.AtualizarOdd(odd);
                    evento.inserirAposta(novaOP);
                    modeloTabela.addRow(new Object[]{
                        novaOP.getNomeOpAposta(),
                        novaOP.getOdd(),
                        novaOP.verResultado(),
                    });
                    textFieldNomeOP.setText("");
                    textFieldOdd.setText("");
                } catch (NumberFormatException numE) {
                    JOptionPane.showMessageDialog(this, "Odd é um numero decimal", "Erro de Input",JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }
    
    }

    public class AtualizarResAposta extends JFrame implements ActionListener{

        JRadioButton botVen,botPer,botAb;
        JButton botIni;

        public AtualizarResAposta(JButton botIni){
            super("Atualizar Resultado da Aposta");
            this.botIni = botIni;
            setSize(300,200);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Atualizar Resultado",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 20));
            add(tit0,BorderLayout.NORTH);

            //RadioButton
            ButtonGroup gp1 = new ButtonGroup();
            botVen = new JRadioButton("Vencedora",false);
            botAb = new JRadioButton("Aberto",true);
            botPer = new JRadioButton("Perdedora",false);
            gp1.add(botVen);
            gp1.add(botAb);
            gp1.add(botPer);

            //Actionlistener
            botVen.addActionListener(this);
            botAb.addActionListener(this);
            botPer.addActionListener(this);
            
            //Painel dos RB
            JPanel painelRB = new JPanel(new FlowLayout());
            painelRB.setBorder(BorderFactory.createTitledBorder("Resultado"));
            painelRB.add(botVen);
            painelRB.add(botAb);
            painelRB.add(botPer);
            

            add(painelRB,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botIni){
                setVisible(true);
            }
            if(e.getSource() == botVen){
                try{
                    OpcaoAposta auxOA = evento.getOpcoesAposta(tabela.getSelectedRow());
                    auxOA.definirResultado(true);
                }catch(IndexOutOfBoundsException indE){
                    JOptionPane.showMessageDialog(new JFrame(), "Indice invalido. Selecione uma linha", "Erro de input",JOptionPane.ERROR_MESSAGE);
                }
                atualizaTabelaMercadoAp();
            }else if(e.getSource() == botPer){
                try{
                    OpcaoAposta auxOA = evento.getOpcoesAposta(tabela.getSelectedRow());
                    auxOA.definirResultado(false);
                }catch(IndexOutOfBoundsException indE){
                    JOptionPane.showMessageDialog(new JFrame(), "Indice invalido. Selecione uma linha", "Erro de input",JOptionPane.ERROR_MESSAGE);
                }
                atualizaTabelaMercadoAp();
            }else if(e.getSource() == botAb){
                try{
                    OpcaoAposta auxOA = evento.getOpcoesAposta(tabela.getSelectedRow());
                    auxOA.setStatus(false);//Aposta Aberta
                }catch(IndexOutOfBoundsException indE){
                    JOptionPane.showMessageDialog(new JFrame(), "Indice invalido. Selecione uma linha", "Erro de input",JOptionPane.ERROR_MESSAGE);
                }
                atualizaTabelaMercadoAp();
            }
            
        }
        
    }

    public class AtualizarResEvento extends JFrame implements ActionListener{

        JRadioButton botVenTA,botVenTB,botAb,botEmpate;
        

        public AtualizarResEvento(){
            super("Atualizar Resultado do Evento");
            setSize(470,170);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Atualizar Resultado",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 20));
            add(tit0,BorderLayout.NORTH);

            //RadioButton
            ButtonGroup gp1 = new ButtonGroup();
            botVenTA = new JRadioButton("Vitoria do " + evento.getTimeA(),false);
            botAb = new JRadioButton("Aberto",true);
            botVenTB = new JRadioButton("Vitoria do " + evento.getTimeB(),false);
            botEmpate = new JRadioButton("Empate",false);
            gp1.add(botVenTA);
            gp1.add(botAb);
            gp1.add(botVenTB);
            gp1.add(botEmpate);

            //Actionlistener
            botVenTA.addActionListener(this);
            botAb.addActionListener(this);
            botVenTB.addActionListener(this);
            botEmpate.addActionListener(this);
            
            //Painel dos RB
            JPanel painelRB = new JPanel(new FlowLayout());
            painelRB.setBorder(BorderFactory.createTitledBorder("Resultado"));
            painelRB.add(botVenTA);
            painelRB.add(botEmpate);
            painelRB.add(botVenTB);
            painelRB.add(botAb);

            add(painelRB,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botAtualizaResEvento){
                setVisible(true);
            }
            if(e.getSource() == botVenTA){
                evento.setResultado(1);
                status.setText("Status: " + evento.VerStatus());
                status.setBackground(Color.RED);
                resultado.setText("Resultado: " + evento.getResultado());
                resultado.setBackground(Color.GREEN);
            }else if(e.getSource() == botVenTB){
                evento.setResultado(2);
                status.setText("Status: " + evento.VerStatus());
                status.setBackground(Color.RED);
                resultado.setText("Resultado: " + evento.getResultado());
                resultado.setBackground(Color.GREEN);
            }else if(e.getSource() == botEmpate){
                evento.setResultado(3);
                status.setText("Status: " + evento.VerStatus());
                status.setBackground(Color.RED);
                resultado.setText("Resultado: " + evento.getResultado());
                resultado.setBackground(Color.GREEN);
            }else if(e.getSource() == botAb){
                evento.setResultado(0);
                evento.setStatus(true);
                status.setText("Status: " + evento.VerStatus());
                status.setBackground(Color.GREEN);
                resultado.setText("Resultado: " + evento.getResultado());
                resultado.setBackground(Color.GRAY);
            }
            
        }
    }

    public void inicializaTabela(){
        for(int i = 0; i < evento.getTamanhoMercadoAp();i++){
            OpcaoAposta auxOA = evento.getOpcoesAposta(i);
            modeloTabela.addRow(new Object[]{
                auxOA.getNomeOpAposta(),
                auxOA.getOdd(),
                auxOA.verResultado(),
            });
        }
    }

    public void atualizaTabelaMercadoAp(){
        try {
            for(int i = 0; i < modeloTabela.getRowCount();i++){
            OpcaoAposta auxOA = evento.getOpcoesAposta(i);
            modeloTabela.setValueAt(auxOA.getOdd(), i, 1);
            modeloTabela.setValueAt(auxOA.verResultado(), i, 2);
        }
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Indice invalido na Atualização", "Erro",JOptionPane.ERROR_MESSAGE);
        }
    }
}
