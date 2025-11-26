/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ConexaoMySQL
 */

package br.uniplan.java_banco_acme.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL
 */
public class ConexaoMySQL {
    
    private static final String URL = "jdbc:mysql://localhost:3306/banco_acme";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    
    private static Connection conexao = null;
    
    /**
     * Obtém uma conexão com o banco de dados
     * @return Connection objeto de conexão
     * @throws SQLException em caso de erro na conexão
     */
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL não encontrado", e);
            }
        }
        return conexao;
    }
    
    /**
     * Fecha a conexão com o banco de dados
     */
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}

