/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TelaPrincipal
 */

package br.uniplan.java_banco_acme.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe que representa a tela principal do sistema
 * Demonstra encapsulamento e uso de componentes Swing
 */
public class TelaPrincipal extends JFrame {
    
    private JButton btnCadastrarCliente;
    private JButton btnCriarConta;
    private JButton btnCriarContaPoupanca;
    private JButton btnDepositar;
    private JButton btnSacar;
    private JButton btnSair;
    
    public TelaPrincipal() {
        inicializarComponentes();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        setTitle("Banco ACME - Sistema Bancário");
        setLayout(new BorderLayout());
        
        // Painel de título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(0, 102, 204));
        JLabel titulo = new JLabel("BANCO ACME", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        painelTitulo.add(titulo);
        painelTitulo.setPreferredSize(new Dimension(0, 60));
        
        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(6, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        painelBotoes.setBackground(new Color(245, 245, 245));
        
        btnCadastrarCliente = new JButton("1 - Cadastrar Cliente");
        btnCriarConta = new JButton("2 - Criar Conta");
        btnCriarContaPoupanca = new JButton("3 - Criar Conta Poupança");
        btnDepositar = new JButton("4 - Depositar");
        btnSacar = new JButton("5 - Sacar");
        btnSair = new JButton("Sair");
        
        // Estilizar botões
        estilizarBotao(btnCadastrarCliente);
        estilizarBotao(btnCriarConta);
        estilizarBotao(btnCriarContaPoupanca);
        estilizarBotao(btnDepositar);
        estilizarBotao(btnSacar);
        btnSair.setBackground(new Color(204, 0, 0));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Arial", Font.BOLD, 14));
        btnSair.setOpaque(true);
        btnSair.setContentAreaFilled(true);
        btnSair.setBorderPainted(true);
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover para botão sair
        btnSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSair.setBackground(new Color(255, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSair.setBackground(new Color(204, 0, 0));
            }
        });
        
        painelBotoes.add(btnCadastrarCliente);
        painelBotoes.add(btnCriarConta);
        painelBotoes.add(btnCriarContaPoupanca);
        painelBotoes.add(btnDepositar);
        painelBotoes.add(btnSacar);
        painelBotoes.add(btnSair);
        
        // Adicionar listeners
        btnCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCadastroCliente().setVisible(true);
            }
        });
        
        btnCriarConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCriarConta().setVisible(true);
            }
        });
        
        btnCriarContaPoupanca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCriarContaPoupanca().setVisible(true);
            }
        });
        
        btnDepositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaDepositar().setVisible(true);
            }
        });
        
        btnSacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaSacar().setVisible(true);
            }
        });
        
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        add(painelTitulo, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
    }
    
    private void estilizarBotao(JButton botao) {
        botao.setBackground(new Color(0, 102, 204));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setPreferredSize(new Dimension(300, 50));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(true);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Adicionar efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(0, 122, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(0, 102, 204));
            }
        });
    }
    
    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Usar Look and Feel que preserva cores customizadas
                    UIManager.put("Button.background", new Color(0, 102, 204));
                    UIManager.put("Button.foreground", Color.WHITE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new TelaPrincipal().setVisible(true);
            }
        });
    }
}

