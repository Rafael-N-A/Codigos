package Visao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.Aposta;
import Modelo.Evento;
import Modelo.OpcaoAposta;
import Modelo.Usuario;
import Persistencia.IDInvalido;
import Persistencia.Persistente;


public class PainelDoUsuario extends JPanel implements ActionListener{
    
    private Font FpadraoBot = new Font("Arial", Font.PLAIN, 15);
    private Dimension dimensionPadraoBot = new Dimension(300,100);
    private JButton botDepositar,botSacar,botVerApostas,botFazerAposta;
    private DefaultTableModel modeloTabela; 
    private JTable tabela;

    private JLabel saldo = new JLabel();

    private Usuario auxUsuario;
    private Persistente<Evento> auxEventos;

    public PainelDoUsuario(ActionListener listenerPainelUsuario,Usuario usuario,Persistente<Evento> eventos){
        super(new BorderLayout());
        usuario.atualizarApostas();
        auxUsuario = usuario;
        auxEventos = eventos;

        //Tabela do historico de aposta
        //Tabela
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Evento");
        modeloTabela.addColumn("Aposta");
        modeloTabela.addColumn("Valor Apostado");
        modeloTabela.addColumn("Valor Potencial");
        modeloTabela.addColumn("Resultado");

        tabela = new JTable(modeloTabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        tabela.getColumnModel().getColumn(1).setMaxWidth(200);
        tabela.getColumnModel().getColumn(2).setMaxWidth(200);
        tabela.getColumnModel().getColumn(3).setMaxWidth(120);
        tabela.getColumnModel().getColumn(4).setMaxWidth(120);
        tabela.getColumnModel().getColumn(5).setMaxWidth(100);

        inicializaTabela();

        //Titulo
        JLabel Titulo = new JLabel("Usuario: " + usuario.getNome(),SwingConstants.CENTER);
        Titulo.setFont(new Font("Arial", Font.BOLD, 45));
        add(Titulo,BorderLayout.NORTH);

        //Painel e Botao voltar
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botVoltar = new JButton("VOLTAR");
        botVoltar.setFont(new Font("Arial", Font.PLAIN, 20));
        botVoltar.setPreferredSize(new Dimension(200,40));
        botVoltar.setActionCommand("PainelUsuarios");
        botVoltar.addActionListener(listenerPainelUsuario);
        painelVoltar.add(botVoltar);

        // Botoes de acao
        botDepositar = new JButton("Depositar");
        botDepositar.setFont(FpadraoBot);
        botDepositar.setPreferredSize(dimensionPadraoBot);
        botSacar = new JButton("Sacar");
        botSacar.setFont(FpadraoBot);
        botSacar.setPreferredSize(dimensionPadraoBot);
        botVerApostas = new JButton("Ver Apostas");
        botVerApostas.setFont(FpadraoBot);
        botVerApostas.setPreferredSize(dimensionPadraoBot);
        botFazerAposta = new JButton("Fazer Aposta");
        botFazerAposta.setFont(FpadraoBot);
        botFazerAposta.setPreferredSize(new Dimension(640,100));

        //Adicoes Actionlistener
        botDepositar.addActionListener(this);
        botSacar.addActionListener(this);
        botFazerAposta.addActionListener(new VisualizarEventos());
        botVerApostas.addActionListener(new VisualizarHistoricoAposta());

        // Detalhes da conta
        saldo.setText("Saldo: R$ " + usuario.VerSaldo());
        saldo.setOpaque(true);
        saldo.setForeground(Color.WHITE);
        saldo.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
        saldo.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        saldo.setBackground(Color.GRAY);

        //Painel Central / Gridbag
        JPanel painelCentral = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 20, 10, 20);

        // Linha 0
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 2;
        painelCentral.add(saldo,gbc);


        //linha 0
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        painelCentral.add(botDepositar,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painelCentral.add(botSacar,gbc);

        //linha 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        painelCentral.add(botVerApostas,gbc);

        //linha 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        painelCentral.add(botFazerAposta,gbc);

        //Adicoes no painel
        add(painelVoltar,BorderLayout.SOUTH);
        add(painelCentral,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == botDepositar){
            try{
                String str = JOptionPane.showInputDialog(this,"Escreva o Valor","Depositar",JOptionPane.PLAIN_MESSAGE);
                if(str == null){return;}
                double valor = Double.parseDouble(str);
                if(valor > 0){
                    auxUsuario.Depositar(valor);
                    saldo.setText("Saldo: R$ " + auxUsuario.VerSaldo());
                }else{
                    JOptionPane.showMessageDialog(new JFrame(),"Digite um valor postivo", "Erro de deposito", JOptionPane.ERROR_MESSAGE);
                }  
            }catch(Exception ex){
                JOptionPane.showMessageDialog(new JFrame(), "Digite um numero decimal", "Erro de input", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(e.getSource() == botSacar){
            try{
                String str = JOptionPane.showInputDialog(this,"Escreva o Valor","Sacar",JOptionPane.PLAIN_MESSAGE);
                if(str == null){return;}
                double valor = Double.parseDouble(str);
                if(valor <= auxUsuario.VerSaldo() && valor > 0){
                    auxUsuario.Sacar(valor);
                    saldo.setText("Saldo: R$ " + auxUsuario.VerSaldo());
                }else{
                    JOptionPane.showMessageDialog(new JFrame(),"Saque indisponivel (Valor maior que o saldo ou Negativo)", "Erro de saque", JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(new JFrame(),"Digite um numero decimal", "Erro de input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class FazerAposta extends JFrame implements ActionListener{

        JTextField textFieldOPAposta,textFieldValorApostar;
        Evento eventoEscolhido;

        public FazerAposta(Evento eventoEscolhido){
            super("Fazer Aposta");
            this.eventoEscolhido = eventoEscolhido;
            setSize(500,500);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            setVisible(true);

            //Titulo
            JLabel tit0 = new JLabel("Fazer Aposta",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);

            //Painel Central
            JPanel painelCentral = new JPanel(new GridLayout(3,0,3,3));

            //JTextArea
            JTextArea textArea = new JTextArea(eventoEscolhido.verApostas());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial",Font.PLAIN,18));
            painelCentral.add(textArea);

            //JtextfieldOPAposta
            JPanel painelOPAposta = new JPanel(new FlowLayout(FlowLayout.CENTER,0,2));
            textFieldOPAposta = new JTextField(20);
            textFieldOPAposta.setFont(new Font("Arial", Font.PLAIN, 20));
            textFieldOPAposta.addActionListener(this);
            JLabel textOPAposta = new JLabel("Digite o indice da Aposta: ");
            textOPAposta.setFont(new Font("Arial", Font.PLAIN, 20));
            painelOPAposta.add(textOPAposta);
            painelOPAposta.add(textFieldOPAposta);
            painelCentral.add(painelOPAposta);

            //JTextFieldValorApostado
            JPanel painelValorApostar = new JPanel(new FlowLayout(FlowLayout.CENTER,0,2));
            textFieldValorApostar = new JTextField(20);
            textFieldValorApostar.setFont(new Font("Arial", Font.PLAIN, 20));
            textFieldValorApostar.addActionListener(this);
            JLabel textValorApostar = new JLabel("Digite o valor há apostar: ");
            textValorApostar.setFont(new Font("Arial", Font.PLAIN, 20));
            painelValorApostar.add(textValorApostar);
            painelValorApostar.add(textFieldValorApostar);
            JLabel textEnter = new JLabel("Enter para confirmar",SwingConstants.CENTER);
            textEnter.setFont(new Font("Arial", Font.BOLD, 10));
            painelValorApostar.add(textEnter);
            painelCentral.add(painelValorApostar);


            add(painelCentral);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String auxIndice = textFieldOPAposta.getText();
                int indice = Integer.parseInt(auxIndice);
                OpcaoAposta auxOPA = eventoEscolhido.getOpcoesAposta(indice);
                String auxValor = textFieldValorApostar.getText();
                double valorApostado = Double.parseDouble(auxValor);
                if(valorApostado < auxUsuario.VerSaldo()){
                    Aposta auxApostaF = new Aposta(auxUsuario, eventoEscolhido, auxOPA, auxUsuario.Sacar(valorApostado));
                    //Add na tabela
                    modeloTabela.addRow(new Object[]{
                        auxApostaF.getID(),
                        auxApostaF.getEvento(),
                        auxApostaF.getNomeAposta(),
                        auxApostaF.getValorApostado(),
                        auxApostaF.getValorPotencial(),
                        auxApostaF.verResultado(),
                    });
                    auxUsuario.inserirAposta(auxApostaF);
                    saldo.setText("Saldo: R$ " + auxUsuario.VerSaldo());
                }else{
                    JOptionPane.showMessageDialog(this, "Valor maior que o saldo", "Erro de saque",JOptionPane.ERROR_MESSAGE);
                }
                textFieldOPAposta.setText("");
                textFieldValorApostar.setText("");
            }catch (NumberFormatException numE) {
                JOptionPane.showMessageDialog(this, "Indice(Inteiro) e Valor(Decimal) são numeros", "Erro de Input",JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    class VisualizarEventos extends JFrame implements ActionListener{

        JButton confirmar;
        JTextField idText;

        public VisualizarEventos(){
            super("Eventos");
            setSize(600,600);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Eventos registrados",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);

            JTextArea textArea = new JTextArea(auxEventos.toString());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial",Font.PLAIN,18));

            JScrollPane scroll = new JScrollPane(textArea);

            //Textfield e botão
            JPanel painelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
            confirmar = new JButton("Confirmar");
            confirmar.addActionListener(this);
            idText = new JTextField(15);
            idText.setFont(new Font("Arial", Font.PLAIN, 15));
            JLabel text0 = new JLabel("ID do Evento:");
            text0.setFont(new Font("Arial", Font.BOLD, 15));
            painelSouth.add(text0);
            painelSouth.add(idText);
            painelSouth.add(confirmar);
            

            add(scroll,BorderLayout.CENTER);
            add(painelSouth,BorderLayout.SOUTH);
        }
        
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botFazerAposta){
                setVisible(true);
            }else if(e.getSource() == confirmar){
                String idStr = idText.getText();
                try {
                    int idInt = Integer.parseInt(idStr);
                    Evento auxE = auxEventos.procurar(idInt);
                    //Condições para o acesso
                    if(auxE.getStatus() == false || auxE.existeAposta() == false){
                        JOptionPane.showMessageDialog(this, "Jogo encerrado ou não há um Mercado de Aposta", "Erro de Acesso",JOptionPane.ERROR_MESSAGE);
                    }else{
                        new FazerAposta(auxE).setVisible(true);
                    }
                } catch (IDInvalido idE) {
                    JOptionPane.showMessageDialog(this, idE, "Erro de ID invalido",JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException numE){
                    JOptionPane.showMessageDialog(this, "ID deve ser um inteiro", "Erro de Input",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public class VisualizarHistoricoAposta extends JFrame implements ActionListener{

        private JButton botApagarAposta;
        
        public VisualizarHistoricoAposta(){
            super("Historico de Apostas");
            setSize(800,600);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            //Titulo
            JLabel tit0 = new JLabel("Histórico de Aposta",SwingConstants.CENTER);
            tit0.setFont(new Font("Arial", Font.BOLD, 25));
            add(tit0,BorderLayout.NORTH);
            
            //Botoes de acao
            botApagarAposta = new JButton("Excluir Aposta");
            botApagarAposta.addActionListener(this);
            

            //Painel gridBag
            JPanel painelCentro = new JPanel(new GridBagLayout());
            var gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.insets = new Insets(20, 5, 10, 5);
            //linha 0
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridwidth = 4;
            painelCentro.add(botApagarAposta,gbc);

            gbc.insets = new Insets(0, 5, 5, 5);
            //linha Jtable
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridwidth = 4;

            var scrool = new JScrollPane();
            scrool.setViewportView(tabela);

            painelCentro.add(scrool,gbc);

            add(painelCentro,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == botVerApostas){
                setVisible(true);
            }else if (e.getSource() == botApagarAposta) {
                try{
                    int auxID = (int)modeloTabela.getValueAt(tabela.getSelectedRow(), 0);
                    auxUsuario.removerAposta(auxID);
                    modeloTabela.removeRow(tabela.getSelectedRow());
                    saldo.setText("Saldo: R$ " + auxUsuario.VerSaldo());
                }catch (IndexOutOfBoundsException outE){
                    JOptionPane.showMessageDialog(new JFrame(), "Erro: Nenhuma linha escolhida", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                }
                tabela.clearSelection();
            }
        }
    }

    public void inicializaTabela(){
        for(int i = 0; i < auxUsuario.getTamHistAposta();i++){
            Aposta auxA = auxUsuario.getAposta(i);
            modeloTabela.addRow(new Object[]{
                    auxA.getID(),
                    auxA.getEvento(),
                    auxA.getNomeAposta(),
                    auxA.getValorApostado(),
                    auxA.getValorPotencial(),
                    auxA.verResultado(),
                });
        }
    }

}
