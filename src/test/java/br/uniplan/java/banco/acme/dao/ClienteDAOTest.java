/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ClienteDAOTest
 */

package br.uniplan.java.banco.acme.dao;

import br.uniplan.java.banco.acme.model.Cliente;
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
class ClienteDAOTest {

    private ClienteDAO clienteDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        clienteDAO = new ClienteDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void testInserirClienteComSucesso() throws SQLException {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "joao@email.com", 
                                      "11999999999", "Rua Teste, 123");
        cliente.setDataCadastro(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean resultado = clienteDAO.inserir(cliente);

            assertTrue(resultado);
            verify(mockPreparedStatement).setString(1, "João Silva");
            verify(mockPreparedStatement).setString(2, "123.456.789-00");
            verify(mockPreparedStatement).setString(3, "joao@email.com");
            verify(mockPreparedStatement).setString(4, "11999999999");
            verify(mockPreparedStatement).setString(5, "Rua Teste, 123");
            verify(mockPreparedStatement).setDate(6, any(java.sql.Date.class));
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testInserirClienteComFalha() throws SQLException {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "joao@email.com", 
                                      "11999999999", "Rua Teste, 123");
        cliente.setDataCadastro(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean resultado = clienteDAO.inserir(cliente);

            assertFalse(resultado);
        }
    }

    @Test
    void testInserirClienteComExcecaoSQL() throws SQLException {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "joao@email.com", 
                                      "11999999999", "Rua Teste, 123");
        cliente.setDataCadastro(new Date());

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Erro de conexão"));

            boolean resultado = clienteDAO.inserir(cliente);

            assertFalse(resultado);
        }
    }

    @Test
    void testBuscarClientePorIdComSucesso() throws SQLException {
        int id = 1;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("nome")).thenReturn("João Silva");
            when(mockResultSet.getString("cpf")).thenReturn("123.456.789-00");
            when(mockResultSet.getString("email")).thenReturn("joao@email.com");
            when(mockResultSet.getString("telefone")).thenReturn("11999999999");
            when(mockResultSet.getString("endereco")).thenReturn("Rua Teste, 123");
            when(mockResultSet.getDate("data_cadastro")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            Cliente cliente = clienteDAO.buscarPorId(id);

            assertNotNull(cliente);
            assertEquals(1, cliente.getId());
            assertEquals("João Silva", cliente.getNome());
            assertEquals("123.456.789-00", cliente.getCpf());
            verify(mockPreparedStatement).setInt(1, id);
        }
    }

    @Test
    void testBuscarClientePorIdNaoEncontrado() throws SQLException {
        int id = 999;

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            Cliente cliente = clienteDAO.buscarPorId(id);

            assertNull(cliente);
        }
    }

    @Test
    void testBuscarClientePorCpfComSucesso() throws SQLException {
        String cpf = "123.456.789-00";

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("nome")).thenReturn("João Silva");
            when(mockResultSet.getString("cpf")).thenReturn(cpf);
            when(mockResultSet.getString("email")).thenReturn("joao@email.com");
            when(mockResultSet.getString("telefone")).thenReturn("11999999999");
            when(mockResultSet.getString("endereco")).thenReturn("Rua Teste, 123");
            when(mockResultSet.getDate("data_cadastro")).thenReturn(new java.sql.Date(System.currentTimeMillis()));

            Cliente cliente = clienteDAO.buscarPorCpf(cpf);

            assertNotNull(cliente);
            assertEquals(cpf, cliente.getCpf());
            verify(mockPreparedStatement).setString(1, cpf);
        }
    }

    @Test
    void testBuscarClientePorCpfNaoEncontrado() throws SQLException {
        String cpf = "999.999.999-99";

        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            Cliente cliente = clienteDAO.buscarPorCpf(cpf);

            assertNull(cliente);
        }
    }

    @Test
    void testListarTodosClientesComSucesso() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, true, false);
            
            // Primeiro cliente
            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getString("nome")).thenReturn("João Silva", "Maria Santos");
            when(mockResultSet.getString("cpf")).thenReturn("123.456.789-00", "987.654.321-00");
            when(mockResultSet.getString("email")).thenReturn("joao@email.com", "maria@email.com");
            when(mockResultSet.getString("telefone")).thenReturn("11999999999", "11888888888");
            when(mockResultSet.getString("endereco")).thenReturn("Rua Teste, 123", "Av. Teste, 456");
            when(mockResultSet.getDate("data_cadastro")).thenReturn(
                new java.sql.Date(System.currentTimeMillis()),
                new java.sql.Date(System.currentTimeMillis())
            );

            List<Cliente> clientes = clienteDAO.listarTodos();

            assertNotNull(clientes);
            assertEquals(2, clientes.size());
            assertEquals("João Silva", clientes.get(0).getNome());
            assertEquals("Maria Santos", clientes.get(1).getNome());
        }
    }

    @Test
    void testListarTodosClientesListaVazia() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            List<Cliente> clientes = clienteDAO.listarTodos();

            assertNotNull(clientes);
            assertTrue(clientes.isEmpty());
        }
    }

    @Test
    void testListarTodosClientesComExcecaoSQL() throws SQLException {
        try (MockedStatic<ConexaoMySQL> mockedConexao = mockStatic(ConexaoMySQL.class)) {
            mockedConexao.when(ConexaoMySQL::getConexao).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro na consulta"));

            List<Cliente> clientes = clienteDAO.listarTodos();

            assertNotNull(clientes);
            assertTrue(clientes.isEmpty());
        }
    }
}

