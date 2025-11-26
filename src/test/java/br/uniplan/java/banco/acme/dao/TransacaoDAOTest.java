/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: TransacaoDAOTest
 */

package br.uniplan.java.banco.acme.dao;

import br.uniplan.java.banco.acme.util.ConexaoMySQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoDAOTest {

    private TransacaoDAO transacaoDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    void setUp() throws SQLException {
        transacaoDAO = new TransacaoDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
    }

    @Test
    void testRegistrarTransacaoContaCorrenteComSucesso() throws SQLException {
        Integer contaId = 1;
        Integer contaPoupancaId = null;
        String tipo = "DEPOSITO";
        double valor = 1000.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = transacaoDAO.registrarTransacao(contaId, contaPoupancaId, tipo, valor);

            assertTrue(resultado);
            verify(mockPreparedStatement).setInt(1, contaId);
            verify(mockPreparedStatement).setNull(2, Types.INTEGER);
            verify(mockPreparedStatement).setString(3, tipo);
            verify(mockPreparedStatement).setDouble(4, valor);
            verify(mockPreparedStatement).setTimestamp(5, any(Timestamp.class));
        }
    }

    @Test
    void testRegistrarTransacaoContaPoupancaComSucesso() throws SQLException {
        Integer contaId = null;
        Integer contaPoupancaId = 1;
        String tipo = "DEPOSITO_POUPANCA";
        double valor = 500.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = transacaoDAO.registrarTransacao(contaId, contaPoupancaId, tipo, valor);

            assertTrue(resultado);
            verify(mockPreparedStatement).setNull(1, Types.INTEGER);
            verify(mockPreparedStatement).setInt(2, contaPoupancaId);
            verify(mockPreparedStatement).setString(3, tipo);
            verify(mockPreparedStatement).setDouble(4, valor);
        }
    }

    @Test
    void testRegistrarTransacaoSaqueComSucesso() throws SQLException {
        Integer contaId = 1;
        Integer contaPoupancaId = null;
        String tipo = "SAQUE";
        double valor = 200.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = transacaoDAO.registrarTransacao(contaId, contaPoupancaId, tipo, valor);

            assertTrue(resultado);
            verify(mockPreparedStatement).setString(3, tipo);
            verify(mockPreparedStatement).setDouble(4, valor);
        }
    }

    @Test
    void testRegistrarTransacaoSaquePoupancaComSucesso() throws SQLException {
        Integer contaId = null;
        Integer contaPoupancaId = 1;
        String tipo = "SAQUE_POUPANCA";
        double valor = 150.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = transacaoDAO.registrarTransacao(contaId, contaPoupancaId, tipo, valor);

            assertTrue(resultado);
            verify(mockPreparedStatement).setString(3, tipo);
        }
    }

    @Test
    void testRegistrarTransacaoComFalha() throws SQLException {
        Integer contaId = 1;
        Integer contaPoupancaId = null;
        String tipo = "DEPOSITO";
        double valor = 1000.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean resultado = transacaoDAO.registrarTransacao(contaId, contaPoupancaId, tipo, valor);

            assertFalse(resultado);
        }
    }

    @Test
    void testRegistrarTransacaoComExcecaoSQL() throws SQLException {
        Integer contaId = 1;
        Integer contaPoupancaId = null;
        String tipo = "DEPOSITO";
        double valor = 1000.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Erro de conexão"));

            boolean resultado = transacaoDAO.registrarTransacao(contaId, contaPoupancaId, tipo, valor);

            assertFalse(resultado);
        }
    }
}

