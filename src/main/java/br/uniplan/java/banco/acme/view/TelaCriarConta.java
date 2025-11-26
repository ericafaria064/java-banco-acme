/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TelaCriarConta
 */

package br.uniplan.java.banco.acme.view;

import br.uniplan.java.banco.acme.dao.ClienteDAO;
import br.uniplan.java.banco.acme.dao.ContaDAO;
import br.uniplan.java.banco.acme.model.Cliente;
import br.uniplan.java.banco.acme.model.ContaCorrente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

/**
 * Tela para criação de conta corrente
 */
public class TelaCriarConta extends JDialog {
    
    private JComboBox<Cliente> cmbClientes;
    private JButton btnCriar;
    private JButton btnCancelar;
    private ClienteDAO clienteDAO;
    private ContaDAO contaDAO;
    
    public TelaCriarConta() {
        super((Frame) null, "Criar Conta", true);
        clienteDAO = new ClienteDAO();
        contaDAO = new ContaDAO();
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
        btnCriar = new JButton("Criar Conta");
        btnCancelar = new JButton("Cancelar");
        
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarConta();
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
    
    private void criarConta() {
        Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar se o cliente já tem uma conta
        ContaCorrente contaExistente = contaDAO.buscarPorClienteId(cliente.getId());
        if (contaExistente != null) {
            JOptionPane.showMessageDialog(this, "Este cliente já possui uma conta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Gerar número de conta aleatório
        String numeroConta = gerarNumeroConta();
        
        ContaCorrente conta = new ContaCorrente(cliente.getId(), numeroConta);
        
        if (contaDAO.inserir(conta)) {
            JOptionPane.showMessageDialog(this, 
                "Conta criada com sucesso!\nNúmero da Conta: " + numeroConta, 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao criar conta!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String gerarNumeroConta() {
        Random random = new Random();
        int numero = 10000 + random.nextInt(90000);
        return String.valueOf(numero);
    }
    
    private void configurarJanela() {
        setSize(400, 150);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

