/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ClienteTest
 */

package br.uniplan.java.banco.acme.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    void testConstrutorPadrao() {
        Cliente cliente = new Cliente();
        assertNotNull(cliente);
        assertEquals(0, cliente.getId());
        assertNull(cliente.getNome());
        assertNull(cliente.getCpf());
    }

    @Test
    void testConstrutorComParametros() {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", 
                                     "joao@email.com", "11999999999", 
                                     "Rua Teste, 123");
        
        assertNotNull(cliente);
        assertEquals("João Silva", cliente.getNome());
        assertEquals("123.456.789-00", cliente.getCpf());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("11999999999", cliente.getTelefone());
        assertEquals("Rua Teste, 123", cliente.getEndereco());
        assertNotNull(cliente.getDataCadastro());
    }

    @Test
    void testGettersESetters() {
        cliente.setId(1);
        cliente.setNome("Maria Santos");
        cliente.setCpf("987.654.321-00");
        cliente.setEmail("maria@email.com");
        cliente.setTelefone("11888888888");
        cliente.setEndereco("Av. Teste, 456");
        Date data = new Date();
        cliente.setDataCadastro(data);

        assertEquals(1, cliente.getId());
        assertEquals("Maria Santos", cliente.getNome());
        assertEquals("987.654.321-00", cliente.getCpf());
        assertEquals("maria@email.com", cliente.getEmail());
        assertEquals("11888888888", cliente.getTelefone());
        assertEquals("Av. Teste, 456", cliente.getEndereco());
        assertEquals(data, cliente.getDataCadastro());
    }

    @Test
    void testToString() {
        cliente.setId(1);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");

        String resultado = cliente.toString();

        assertNotNull(resultado);
        assertTrue(resultado.contains("Cliente"));
        assertTrue(resultado.contains("id=1"));
        assertTrue(resultado.contains("nome=João Silva"));
        assertTrue(resultado.contains("cpf=123.456.789-00"));
    }

    @Test
    void testEncapsulamento() {
        // Teste de encapsulamento - verificar que os atributos são privados
        // e só podem ser acessados através dos getters e setters
        cliente.setId(10);
        assertEquals(10, cliente.getId());

        cliente.setNome("Teste");
        assertEquals("Teste", cliente.getNome());
    }
}

