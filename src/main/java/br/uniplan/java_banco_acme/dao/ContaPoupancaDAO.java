/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaPoupancaDAO
 */

package br.uniplan.java_banco_acme.dao;

import br.uniplan.java_banco_acme.model.ContaPoupanca;
import br.uniplan.java_banco_acme.util.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para operações com Contas Poupança
 */
public class ContaPoupancaDAO {
    
    /**
     * Insere uma nova conta poupança no banco de dados
     * @param contaPoupanca objeto ContaPoupanca a ser inserido
     * @return true se inserido com sucesso
     */
    public boolean inserir(ContaPoupanca contaPoupanca) {
        String sql = "INSERT INTO contas_poupanca (conta_id, numero_poupanca, saldo, data_criacao) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, contaPoupanca.getContaId());
            stmt.setString(2, contaPoupanca.getNumeroConta());
            stmt.setDouble(3, contaPoupanca.getSaldo());
            stmt.setDate(4, new java.sql.Date(contaPoupanca.getDataCriacao().getTime()));
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    contaPoupanca.setId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir conta poupança: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Busca uma conta poupança pelo ID
     * @param id ID da conta poupança
     * @return ContaPoupanca encontrada ou null
     */
    public ContaPoupanca buscarPorId(int id) {
        String sql = "SELECT * FROM contas_poupanca WHERE id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarContaPoupancaDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta poupança: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca uma conta poupança pelo ID da conta corrente
     * @param contaId ID da conta corrente
     * @return ContaPoupanca encontrada ou null
     */
    public ContaPoupanca buscarPorContaId(int contaId) {
        String sql = "SELECT * FROM contas_poupanca WHERE conta_id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarContaPoupancaDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta poupança por conta: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca uma conta poupança pelo número da conta
     * @param numeroPoupanca número da conta poupança
     * @return ContaPoupanca encontrada ou null
     */
    public ContaPoupanca buscarPorNumero(String numeroPoupanca) {
        String sql = "SELECT * FROM contas_poupanca WHERE numero_poupanca = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, numeroPoupanca);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarContaPoupancaDoResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta poupança por número: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Atualiza o saldo de uma conta poupança
     * @param contaPoupancaId ID da conta poupança
     * @param novoSaldo novo saldo
     * @return true se atualizado com sucesso
     */
    public boolean atualizarSaldo(int contaPoupancaId, double novoSaldo) {
        String sql = "UPDATE contas_poupanca SET saldo = ? WHERE id = ?";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, contaPoupancaId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar saldo da conta poupança: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Lista todas as contas poupança
     * @return Lista de contas poupança
     */
    public List<ContaPoupanca> listarTodas() {
        List<ContaPoupanca> contas = new ArrayList<>();
        String sql = "SELECT * FROM contas_poupanca ORDER BY numero_poupanca";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                contas.add(criarContaPoupancaDoResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar contas poupança: " + e.getMessage());
        }
        
        return contas;
    }
    
    /**
     * Cria um objeto ContaPoupanca a partir de um ResultSet
     * @param rs ResultSet com os dados
     * @return ContaPoupanca criada
     * @throws SQLException em caso de erro
     */
    private ContaPoupanca criarContaPoupancaDoResultSet(ResultSet rs) throws SQLException {
        ContaPoupanca conta = new ContaPoupanca();
        conta.setId(rs.getInt("id"));
        conta.setContaId(rs.getInt("conta_id"));
        conta.setClienteId(0); // Não está na tabela, pode ser buscado via conta_id
        conta.setNumeroConta(rs.getString("numero_poupanca"));
        conta.setSaldo(rs.getDouble("saldo"));
        conta.setDataCriacao(rs.getDate("data_criacao"));
        return conta;
    }
}

