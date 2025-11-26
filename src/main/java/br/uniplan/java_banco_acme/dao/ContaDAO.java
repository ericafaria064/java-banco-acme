/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaDAO
 */

package br.uniplan.java_banco_acme.dao;

import br.uniplan.java_banco_acme.model.ContaCorrente;
import br.uniplan.java_banco_acme.util.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para operações com Contas Correntes
 */
public class ContaDAO {
    
    /**
     * Insere uma nova conta corrente no banco de dados
     * @param conta objeto ContaCorrente a ser inserido
     * @return true se inserido com sucesso
     */
    public boolean inserir(ContaCorrente conta) {
        String sql = "INSERT INTO contas (cliente_id, numero_conta, saldo, data_criacao) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, conta.getClienteId());
            stmt.setString(2, conta.getNumeroConta());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setDate(4, new java.sql.Date(conta.getDataCriacao().getTime()));
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    conta.setId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir conta: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Busca uma conta pelo ID
     * @param id ID da conta
     * @return ContaCorrente encontrada ou null
     */
    public ContaCorrente buscarPorId(int id) {
        String sql = "SELECT * FROM contas WHERE id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarContaDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca uma conta pelo número da conta
     * @param numeroConta número da conta
     * @return ContaCorrente encontrada ou null
     */
    public ContaCorrente buscarPorNumero(String numeroConta) {
        String sql = "SELECT * FROM contas WHERE numero_conta = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, numeroConta);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarContaDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta por número: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca uma conta pelo ID do cliente
     * @param clienteId ID do cliente
     * @return ContaCorrente encontrada ou null
     */
    public ContaCorrente buscarPorClienteId(int clienteId) {
        String sql = "SELECT * FROM contas WHERE cliente_id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarContaDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta por cliente: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Atualiza o saldo de uma conta
     * @param contaId ID da conta
     * @param novoSaldo novo saldo
     * @return true se atualizado com sucesso
     */
    public boolean atualizarSaldo(int contaId, double novoSaldo) {
        String sql = "UPDATE contas SET saldo = ? WHERE id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, contaId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar saldo: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Lista todas as contas
     * @return Lista de contas
     */
    public List<ContaCorrente> listarTodas() {
        List<ContaCorrente> contas = new ArrayList<>();
        String sql = "SELECT * FROM contas ORDER BY numero_conta";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                contas.add(criarContaDoResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar contas: " + e.getMessage());
        }
        
        return contas;
    }
    
    /**
     * Cria um objeto ContaCorrente a partir de um ResultSet
     * @param rs ResultSet com os dados
     * @return ContaCorrente criada
     * @throws SQLException em caso de erro
     */
    private ContaCorrente criarContaDoResultSet(ResultSet rs) throws SQLException {
        ContaCorrente conta = new ContaCorrente();
        conta.setId(rs.getInt("id"));
        conta.setClienteId(rs.getInt("cliente_id"));
        conta.setNumeroConta(rs.getString("numero_conta"));
        conta.setSaldo(rs.getDouble("saldo"));
        conta.setDataCriacao(rs.getDate("data_criacao"));
        return conta;
    }
}

