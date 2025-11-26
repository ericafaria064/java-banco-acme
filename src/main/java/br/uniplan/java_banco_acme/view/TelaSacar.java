/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TelaSacar
 */

package br.uniplan.java_banco_acme.view;

import br.uniplan.java_banco_acme.dao.ContaDAO;
import br.uniplan.java_banco_acme.dao.ContaPoupancaDAO;
import br.uniplan.java_banco_acme.dao.TransacaoDAO;
import br.uniplan.java_banco_acme.model.ContaCorrente;
import br.uniplan.java_banco_acme.model.ContaPoupanca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela para realizar saques
 */
public class TelaSacar extends JDialog {
    
    private JTextField txtNumeroConta;
    private JTextField txtValor;
    private JRadioButton rbContaCorrente;
    private JRadioButton rbContaPoupanca;
    private JButton btnSacar;
    private JButton btnCancelar;
    private ContaDAO contaDAO;
    private ContaPoupancaDAO contaPoupancaDAO;
    private TransacaoDAO transacaoDAO;
    
    public TelaSacar() {
        super((Frame) null, "Sacar", true);
        contaDAO = new ContaDAO();
        contaPoupancaDAO = new ContaPoupancaDAO();
        transacaoDAO = new TransacaoDAO();
        inicializarComponentes();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tipo de conta
        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("Tipo de Conta:"), gbc);
        gbc.gridx = 1;
        JPanel painelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup grupo = new ButtonGroup();
        rbContaCorrente = new JRadioButton("Conta Corrente", true);
        rbContaPoupanca = new JRadioButton("Conta Poupança");
        grupo.add(rbContaCorrente);
        grupo.add(rbContaPoupanca);
        painelRadio.add(rbContaCorrente);
        painelRadio.add(rbContaPoupanca);
        painelCampos.add(painelRadio, gbc);
        
        // Número da conta
        gbc.gridx = 0; gbc.gridy = 1;
        painelCampos.add(new JLabel("Número da Conta:"), gbc);
        gbc.gridx = 1;
        txtNumeroConta = new JTextField(20);
        txtNumeroConta.setEnabled(true);
        txtNumeroConta.setEditable(true);
        txtNumeroConta.setBackground(Color.WHITE);
        painelCampos.add(txtNumeroConta, gbc);
        
        // Valor
        gbc.gridx = 0; gbc.gridy = 2;
        painelCampos.add(new JLabel("Valor (R$):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(20);
        txtValor.setEnabled(true);
        txtValor.setEditable(true);
        txtValor.setBackground(Color.WHITE);
        painelCampos.add(txtValor, gbc);
        
        JPanel painelBotoes = new JPanel();
        btnSacar = new JButton("Sacar");
        btnCancelar = new JButton("Cancelar");
        
        // Estilizar botões
        estilizarBotao(btnSacar, new Color(204, 0, 0));
        estilizarBotao(btnCancelar, new Color(150, 150, 150));
        
        btnSacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarSaque();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        painelBotoes.add(btnSacar);
        painelBotoes.add(btnCancelar);
        
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void realizarSaque() {
        String numeroConta = txtNumeroConta.getText().trim();
        String valorStr = txtValor.getText().trim();
        
        if (numeroConta.isEmpty() || valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (rbContaCorrente.isSelected()) {
                ContaCorrente conta = contaDAO.buscarPorNumero(numeroConta);
                if (conta == null) {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (conta.sacar(valor)) {
                    contaDAO.atualizarSaldo(conta.getId(), conta.getSaldo());
                    transacaoDAO.registrarTransacao(conta.getId(), null, "SAQUE", valor);
                    JOptionPane.showMessageDialog(this, 
                        "Saque realizado com sucesso!\nNovo saldo: R$ " + String.format("%.2f", conta.getSaldo()), 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Saldo insuficiente ou valor inválido!", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                ContaPoupanca contaPoupanca = contaPoupancaDAO.buscarPorNumero(numeroConta);
                if (contaPoupanca == null) {
                    JOptionPane.showMessageDialog(this, "Conta poupança não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (contaPoupanca.sacar(valor)) {
                    contaPoupancaDAO.atualizarSaldo(contaPoupanca.getId(), contaPoupanca.getSaldo());
                    transacaoDAO.registrarTransacao(null, contaPoupanca.getId(), "SAQUE_POUPANCA", valor);
                    JOptionPane.showMessageDialog(this, 
                        "Saque realizado com sucesso!\nNovo saldo: R$ " + String.format("%.2f", contaPoupanca.getSaldo()), 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Saldo insuficiente ou valor inválido!", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNumeroConta.setText("");
        txtValor.setText("");
    }
    
    private void estilizarBotao(JButton botao, Color cor) {
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(true);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(100, 35));
    }
    
    private void configurarJanela() {
        setSize(450, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        // Garantir que os campos recebam foco
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                txtNumeroConta.requestFocus();
            }
        });
    }
}

