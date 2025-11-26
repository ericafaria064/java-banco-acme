/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ClienteDAO
 */

package br.uniplan.java_banco_acme.dao;

import br.uniplan.java_banco_acme.model.Cliente;
import br.uniplan.java_banco_acme.util.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO (Data Access Object) para operações com Clientes
 * Demonstra encapsulamento ao centralizar operações de banco de dados
 */
public class ClienteDAO {
    
    /**
     * Insere um novo cliente no banco de dados
     * @param cliente objeto Cliente a ser inserido
     * @return true se inserido com sucesso
     */
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, email, telefone, endereco, data_cadastro) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDate(6, new java.sql.Date(cliente.getDataCadastro().getTime()));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca um cliente pelo ID
     * @param id ID do cliente
     * @return Cliente encontrado ou null
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarClienteDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca um cliente pelo CPF
     * @param cpf CPF do cliente
     * @return Cliente encontrado ou null
     */
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarClienteDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Lista todos os clientes
     * @return Lista de clientes
     */
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nome";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(criarClienteDoResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    /**
     * Cria um objeto Cliente a partir de um ResultSet
     * @param rs ResultSet com os dados
     * @return Cliente criado
     * @throws SQLException em caso de erro
     */
    private Cliente criarClienteDoResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setDataCadastro(rs.getDate("data_cadastro"));
        return cliente;
    }
}

