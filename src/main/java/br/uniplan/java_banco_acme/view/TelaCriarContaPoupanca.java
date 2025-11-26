/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TelaCriarContaPoupanca
 */

package br.uniplan.java_banco_acme.view;

import br.uniplan.java_banco_acme.dao.ClienteDAO;
import br.uniplan.java_banco_acme.dao.ContaDAO;
import br.uniplan.java_banco_acme.dao.ContaPoupancaDAO;
import br.uniplan.java_banco_acme.model.Cliente;
import br.uniplan.java_banco_acme.model.ContaCorrente;
import br.uniplan.java_banco_acme.model.ContaPoupanca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

/**
 * Tela para criação de conta poupança
 */
public class TelaCriarContaPoupanca extends JDialog {
    
    private JComboBox<Cliente> cmbClientes;
    private JButton btnCriar;
    private JButton btnCancelar;
    private ClienteDAO clienteDAO;
    private ContaDAO contaDAO;
    private ContaPoupancaDAO contaPoupancaDAO;
    
    public TelaCriarContaPoupanca() {
        super((Frame) null, "Criar Conta Poupança", true);
        clienteDAO = new ClienteDAO();
        contaDAO = new ContaDAO();
        contaPoupancaDAO = new ContaPoupancaDAO();
        inicializarComponentes();
        configurarJanela();
        carregarClientes();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("Selecione o Cliente:"), gbc);
        gbc.gridx = 1;
        cmbClientes = new JComboBox<>();
        cmbClientes.setPreferredSize(new Dimension(250, 25));
        painelCampos.add(cmbClientes, gbc);
        
        JPanel painelBotoes = new JPanel();
        btnCriar = new JButton("Criar Conta Poupança");
        btnCancelar = new JButton("Cancelar");
        
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarContaPoupanca();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        painelBotoes.add(btnCriar);
        painelBotoes.add(btnCancelar);
        
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void carregarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
        for (Cliente cliente : clientes) {
            model.addElement(cliente);
        }
        cmbClientes.setModel(model);
    }
    
    private void criarContaPoupanca() {
        Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar se o cliente tem uma conta corrente
        ContaCorrente conta = contaDAO.buscarPorClienteId(cliente.getId());
        if (conta == null) {
            JOptionPane.showMessageDialog(this, 
                "Para criar uma conta poupança é necessário primeiro criar uma conta no Banco ACME", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar se já existe conta poupança
        ContaPoupanca contaPoupancaExistente = contaPoupancaDAO.buscarPorContaId(conta.getId());
        if (contaPoupancaExistente != null) {
            JOptionPane.showMessageDialog(this, "Este cliente já possui uma conta poupança!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Gerar número de conta poupança
        String numeroPoupanca = gerarNumeroPoupanca();
        
        ContaPoupanca contaPoupanca = new ContaPoupanca(conta.getId(), cliente.getId(), numeroPoupanca);
        
        if (contaPoupancaDAO.inserir(contaPoupanca)) {
            JOptionPane.showMessageDialog(this, 
                "Conta poupança criada com sucesso", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erro ao criar conta poupança", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String gerarNumeroPoupanca() {
        Random random = new Random();
        int numero = 20000 + random.nextInt(80000);
        return String.valueOf(numero);
    }
    
    private void configurarJanela() {
        setSize(400, 150);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

