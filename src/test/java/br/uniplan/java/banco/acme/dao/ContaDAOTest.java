/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaDAOTest
 */

package br.uniplan.java.banco.acme.dao;

import br.uniplan.java.banco.acme.model.ContaCorrente;
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
class ContaDAOTest {

    private ContaDAO contaDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        contaDAO = new ContaDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void testInserirContaComSucesso() throws SQLException {
        ContaCorrente conta = new ContaCorrente(1, "12345");
        conta.setDataCriacao(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(10);

            boolean resultado = contaDAO.inserir(conta);

            assertTrue(resultado);
            assertEquals(10, conta.getId());
            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).setString(2, "12345");
            verify(mockPreparedStatement).setDouble(3, 0.0);
            verify(mockPreparedStatement).setDate(4, any(java.sql.Date.class));
        }
    }

    @Test
    void testInserirContaComFalha() throws SQLException {
        ContaCorrente conta = new ContaCorrente(1, "12345");
        conta.setDataCriacao(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean resultado = contaDAO.inserir(conta);

            assertFalse(resultado);
        }
    }

    @Test
    void testInserirContaComExcecaoSQL() throws SQLException {
        ContaCorrente conta = new ContaCorrente(1, "12345");
        conta.setDataCriacao(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenThrow(new SQLException("Erro de conexão"));

            boolean resultado = contaDAO.inserir(conta);

            assertFalse(resultado);
        }
    }

    @Test
    void testBuscarContaPorIdComSucesso() throws SQLException {
        int id = 1;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("cliente_id")).thenReturn(1);
            when(mockResultSet.getString("numero_conta")).thenReturn("12345");
            when(mockResultSet.getDouble("saldo")).thenReturn(1000.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            ContaCorrente conta = contaDAO.buscarPorId(id);

            assertNotNull(conta);
            assertEquals(1, conta.getId());
            assertEquals("12345", conta.getNumeroConta());
            assertEquals(1000.0, conta.getSaldo());
            verify(mockPreparedStatement).setInt(1, id);
        }
    }

    @Test
    void testBuscarContaPorIdNaoEncontrado() throws SQLException {
        int id = 999;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            ContaCorrente conta = contaDAO.buscarPorId(id);

            assertNull(conta);
        }
    }

    @Test
    void testBuscarContaPorNumeroComSucesso() throws SQLException {
        String numeroConta = "12345";

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("cliente_id")).thenReturn(1);
            when(mockResultSet.getString("numero_conta")).thenReturn(numeroConta);
            when(mockResultSet.getDouble("saldo")).thenReturn(500.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            ContaCorrente conta = contaDAO.buscarPorNumero(numeroConta);

            assertNotNull(conta);
            assertEquals(numeroConta, conta.getNumeroConta());
            verify(mockPreparedStatement).setString(1, numeroConta);
        }
    }

    @Test
    void testBuscarContaPorClienteIdComSucesso() throws SQLException {
        int clienteId = 1;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getInt("cliente_id")).thenReturn(clienteId);
            when(mockResultSet.getString("numero_conta")).thenReturn("12345");
            when(mockResultSet.getDouble("saldo")).thenReturn(750.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            ContaCorrente conta = contaDAO.buscarPorClienteId(clienteId);

            assertNotNull(conta);
            assertEquals(clienteId, conta.getClienteId());
            verify(mockPreparedStatement).setInt(1, clienteId);
        }
    }

    @Test
    void testAtualizarSaldoComSucesso() throws SQLException {
        int contaId = 1;
        double novoSaldo = 2000.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = contaDAO.atualizarSaldo(contaId, novoSaldo);

            assertTrue(resultado);
            verify(mockPreparedStatement).setDouble(1, novoSaldo);
            verify(mockPreparedStatement).setInt(2, contaId);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testAtualizarSaldoComFalha() throws SQLException {
        int contaId = 1;
        double novoSaldo = 2000.0;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean resultado = contaDAO.atualizarSaldo(contaId, novoSaldo);

            assertFalse(resultado);
        }
    }

    @Test
    void testListarTodasContasComSucesso() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, true, false);
            
            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getInt("cliente_id")).thenReturn(1, 2);
            when(mockResultSet.getString("numero_conta")).thenReturn("12345", "67890");
            when(mockResultSet.getDouble("saldo")).thenReturn(1000.0, 2000.0);
            when(mockResultSet.getDate("data_criacao")).thenReturn(
                new java.sql.Date(System.currentTimeMillis()),
                new java.sql.Date(System.currentTimeMillis())
            );

            List<ContaCorrente> contas = contaDAO.listarTodas();

            assertNotNull(contas);
            assertEquals(2, contas.size());
            assertEquals("12345", contas.get(0).getNumeroConta());
            assertEquals("67890", contas.get(1).getNumeroConta());
        }
    }

    @Test
    void testListarTodasContasListaVazia() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            List<ContaCorrente> contas = contaDAO.listarTodas();

            assertNotNull(contas);
            assertTrue(contas.isEmpty());
        }
    }
}

