/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TransacaoDAO
 */

package br.uniplan.java.banco.acme.dao;

import br.uniplan.java.banco.acme.util.ConexaoMySQL;
import java.sql.*;

/**
 * Classe DAO para operações com Transações
 */
public class TransacaoDAO {
    
    /**
     * Registra uma transação no banco de dados
     * @param contaId ID da conta (pode ser null se for conta poupança)
     * @param contaPoupancaId ID da conta poupança (pode ser null se for conta corrente)
     * @param tipo tipo da transação (DEPOSITO, SAQUE, DEPOSITO_POUPANCA, SAQUE_POUPANCA)
     * @param valor valor da transação
     * @return true se registrado com sucesso
     */
    public boolean registrarTransacao(Integer contaId, Integer contaPoupancaId, String tipo, double valor) {
        String sql = "INSERT INTO transacoes (conta_id, conta_poupanca_id, tipo, valor, data_transacao) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (contaId != null) {
                stmt.setInt(1, contaId);
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            
            if (contaPoupancaId != null) {
                stmt.setInt(2, contaPoupancaId);
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            
            stmt.setString(3, tipo);
            stmt.setDouble(4, valor);
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao registrar transação: " + e.getMessage());
        }
        
        return false;
    }
}

