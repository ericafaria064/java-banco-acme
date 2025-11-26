/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaCorrenteTest
 */

package br.uniplan.java.banco.acme.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContaCorrenteTest {

    private ContaCorrente conta;

    @BeforeEach
    void setUp() {
        conta = new ContaCorrente();
    }

    @Test
    void testConstrutorPadrao() {
        ContaCorrente conta = new ContaCorrente();
        assertNotNull(conta);
        assertEquals(0.0, conta.getSaldo());
        assertNotNull(conta.getDataCriacao());
    }

    @Test
    void testConstrutorComParametros() {
        ContaCorrente conta = new ContaCorrente(1, "12345");
        
        assertNotNull(conta);
        assertEquals(1, conta.getClienteId());
        assertEquals("12345", conta.getNumeroConta());
        assertEquals(0.0, conta.getSaldo());
        assertNotNull(conta.getDataCriacao());
    }

    @Test
    void testDepositarValorPositivo() {
        conta.setSaldo(100.0);
        boolean resultado = conta.depositar(50.0);

        assertTrue(resultado);
        assertEquals(150.0, conta.getSaldo());
    }

    @Test
    void testDepositarValorZero() {
        conta.setSaldo(100.0);
        boolean resultado = conta.depositar(0.0);

        assertFalse(resultado);
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testDepositarValorNegativo() {
        conta.setSaldo(100.0);
        boolean resultado = conta.depositar(-50.0);

        assertFalse(resultado);
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testDepositarEmContaVazia() {
        conta.setSaldo(0.0);
        boolean resultado = conta.depositar(100.0);

        assertTrue(resultado);
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testSacarValorValido() {
        conta.setSaldo(100.0);
        boolean resultado = conta.sacar(50.0);

        assertTrue(resultado);
        assertEquals(50.0, conta.getSaldo());
    }

    @Test
    void testSacarValorIgualAoSaldo() {
        conta.setSaldo(100.0);
        boolean resultado = conta.sacar(100.0);

        assertTrue(resultado);
        assertEquals(0.0, conta.getSaldo());
    }

    @Test
    void testSacarValorMaiorQueSaldo() {
        conta.setSaldo(100.0);
        boolean resultado = conta.sacar(150.0);

        assertFalse(resultado);
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testSacarValorZero() {
        conta.setSaldo(100.0);
        boolean resultado = conta.sacar(0.0);

        assertFalse(resultado);
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testSacarValorNegativo() {
        conta.setSaldo(100.0);
        boolean resultado = conta.sacar(-50.0);

        assertFalse(resultado);
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testGettersESetters() {
        conta.setId(1);
        conta.setClienteId(10);
        conta.setNumeroConta("12345");
        conta.setSaldo(500.0);
        Date data = new Date();
        conta.setDataCriacao(data);

        assertEquals(1, conta.getId());
        assertEquals(10, conta.getClienteId());
        assertEquals("12345", conta.getNumeroConta());
        assertEquals(500.0, conta.getSaldo());
        assertEquals(data, conta.getDataCriacao());
    }

    @Test
    void testHerancaDeConta() {
        // Verificar que ContaCorrente é uma instância de Conta
        assertTrue(conta instanceof Conta);
    }

    @Test
    void testPolimorfismoDepositar() {
        Conta contaPolimorfica = new ContaCorrente();
        boolean resultado = contaPolimorfica.depositar(100.0);

        assertTrue(resultado);
        assertEquals(100.0, contaPolimorfica.getSaldo());
    }

    @Test
    void testPolimorfismoSacar() {
        Conta contaPolimorfica = new ContaCorrente();
        contaPolimorfica.setSaldo(100.0);
        boolean resultado = contaPolimorfica.sacar(50.0);

        assertTrue(resultado);
        assertEquals(50.0, contaPolimorfica.getSaldo());
    }

    @Test
    void testMultiplosDepositos() {
        conta.setSaldo(0.0);
        conta.depositar(100.0);
        conta.depositar(50.0);
        conta.depositar(25.0);

        assertEquals(175.0, conta.getSaldo());
    }

    @Test
    void testMultiplosSaques() {
        conta.setSaldo(200.0);
        conta.sacar(50.0);
        conta.sacar(30.0);
        conta.sacar(20.0);

        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void testSequenciaDepositoSaque() {
        conta.setSaldo(0.0);
        conta.depositar(200.0);
        conta.sacar(50.0);
        conta.depositar(100.0);
        conta.sacar(75.0);

        assertEquals(175.0, conta.getSaldo());
    }
}

