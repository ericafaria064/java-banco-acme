/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaPoupancaDAOTest
 */

package br.uniplan.java.banco.acme.dao;

import br.uniplan.java.banco.acme.model.ContaPoupanca;
import br.uniplan.java.banco.acme.util.ConexaoMySQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaPoupancaDAOTest {

    private ContaPoupancaDAO contaPoupancaDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        contaPoupancaDAO = new ContaPoupancaDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void testInserirContaPoupancaComSucesso() throws SQLException {
        ContaPoupanca contaPoupanca = new ContaPoupanca(1, 1, "20001");
        contaPoupanca.setDataCriacao(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(5);

            boolean resultado = contaPoupancaDAO.inserir(contaPoupanca);

            assertTrue(resultado);
            assertEquals(5, contaPoupanca.getId());
            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).setString(2, "20001");
            verify(mockPreparedStatement).setDouble(3, 0.0);
        }
    }

    @Test
    void testInserirContaPoupancaComFalha() throws SQLException {
        ContaPoupanca contaPoupanca = new ContaPoupanca(1, 1, "20001");
        contaPoupanca.setDataCriacao(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean resultado = contaPoupancaDAO.inserir(contaPoupanca);

            assertFalse(resultado);
        }
    }

    @Test
    void testBuscarContaPoupancaPorIdComSucesso() throws SQLException {
        int id = 1;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("conta_id")).thenReturn(1);
            when(mockResultSet.getString("numero_poupanca")).thenReturn("20001");
            when(mockResultSet.getDouble("saldo")).thenReturn(500.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            ContaPoupanca contaPoupanca = contaPoupancaDAO.buscarPorId(id);

            assertNotNull(contaPoupanca);
            assertEquals(1, contaPoupanca.getId());
            assertEquals("20001", contaPoupanca.getNumeroConta());
            assertEquals(1, contaPoupanca.getContaId());
        }
    }

    @Test
    void testBuscarContaPoupancaPorContaIdComSucesso() throws SQLException {
        int contaId = 1;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("conta_id")).thenReturn(contaId);
            when(mockResultSet.getString("numero_poupanca")).thenReturn("20001");
            when(mockResultSet.getDouble("saldo")).thenReturn(1000.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            ContaPoupanca contaPoupanca = contaPoupancaDAO.buscarPorContaId(contaId);

            assertNotNull(contaPoupanca);
            assertEquals(contaId, contaPoupanca.getContaId());
            verify(mockPreparedStatement).setInt(1, contaId);
        }
    }

    @Test
    void testBuscarContaPoupancaPorNumeroComSucesso() throws SQLException {
        String numeroPoupanca = "20001";

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("conta_id")).thenReturn(1);
            when(mockResultSet.getString("numero_poupanca")).thenReturn(numeroPoupanca);
            when(mockResultSet.getDouble("saldo")).thenReturn(750.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            ContaPoupanca contaPoupanca = contaPoupancaDAO.buscarPorNumero(numeroPoupanca);

            assertNotNull(contaPoupanca);
            assertEquals(numeroPoupanca, contaPoupanca.getNumeroConta());
            verify(mockPreparedStatement).setString(1, numeroPoupanca);
        }
    }

    @Test
    void testAtualizarSaldoContaPoupancaComSucesso() throws SQLException {
        int contaPoupancaId = 1;
        double novoSaldo = 3000.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = contaPoupancaDAO.atualizarSaldo(contaPoupancaId, novoSaldo);

            assertTrue(resultado);
            verify(mockPreparedStatement).setDouble(1, novoSaldo);
            verify(mockPreparedStatement).setInt(2, contaPoupancaId);
        }
    }

    @Test
    void testListarTodasContasPoupancaComSucesso() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, true, false);
            
            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getInt("conta_id")).thenReturn(1, 2);
            when(mockResultSet.getString("numero_poupanca")).thenReturn("20001", "20002");
            when(mockResultSet.getDouble("saldo")).thenReturn(1000.0, 2000.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(
                new java.sql.Date(System.currentTimeMillis()),
                new java.sql.Date(System.currentTimeMillis())
            );

            List<ContaPoupanca> contas = contaPoupancaDAO.listarTodas();

            assertNotNull(contas);
            assertEquals(2, contas.size());
            assertEquals("20001", contas.get(0).getNumeroConta());
            assertEquals("20002", contas.get(1).getNumeroConta());
        }
    }

    @Test
    void testListarTodasContasPoupancaListaVazia() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            List<ContaPoupanca> contas = contaPoupancaDAO.listarTodas();

            assertNotNull(contas);
            assertTrue(contas.isEmpty());
        }
    }
}

