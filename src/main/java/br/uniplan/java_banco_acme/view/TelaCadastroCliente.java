/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TelaCadastroCliente
 */

package br.uniplan.java_banco_acme.view;

import br.uniplan.java_banco_acme.dao.ClienteDAO;
import br.uniplan.java_banco_acme.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela para cadastro de clientes
 */
public class TelaCadastroCliente extends JDialog {
    
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtEndereco;
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    public TelaCadastroCliente() {
        super((Frame) null, "Cadastrar Cliente", true);
        inicializarComponentes();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        // Painel de campos
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painelCampos.add(txtNome, gbc);
        
        // CPF
        gbc.gridx = 0; gbc.gridy = 1;
        painelCampos.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(20);
        painelCampos.add(txtCpf, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        painelCampos.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        painelCampos.add(txtEmail, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 3;
        painelCampos.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        painelCampos.add(txtTelefone, gbc);
        
        // Endereço
        gbc.gridx = 0; gbc.gridy = 4;
        painelCampos.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        txtEndereco = new JTextField(20);
        painelCampos.add(txtEndereco, gbc);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCliente();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void salvarCliente() {
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(txtNome.getText().trim());
        cliente.setCpf(txtCpf.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setTelefone(txtTelefone.getText().trim());
        cliente.setEndereco(txtEndereco.getText().trim());
        cliente.setDataCadastro(new java.util.Date());
        
        ClienteDAO dao = new ClienteDAO();
        if (dao.inserir(cliente)) {
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente. Verifique se o CPF já não está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
    }
    
    private void configurarJanela() {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

