/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaPoupancaTest
 */

package br.uniplan.java.banco.acme.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContaPoupancaTest {

    private ContaPoupanca contaPoupanca;

    @BeforeEach
    void setUp() {
        contaPoupanca = new ContaPoupanca();
    }

    @Test
    void testConstrutorPadrao() {
        ContaPoupanca conta = new ContaPoupanca();
        assertNotNull(conta);
        assertEquals(0.0, conta.getSaldo());
        assertNotNull(conta.getDataCriacao());
        assertEquals(0, conta.getContaId());
    }

    @Test
    void testConstrutorComParametros() {
        ContaPoupanca conta = new ContaPoupanca(1, 10, "20001");
        
        assertNotNull(conta);
        assertEquals(1, conta.getContaId());
        assertEquals(10, conta.getClienteId());
        assertEquals("20001", conta.getNumeroConta());
        assertEquals(0.0, conta.getSaldo());
        assertNotNull(conta.getDataCriacao());
    }

    @Test
    void testDepositarValorPositivo() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.depositar(50.0);

        assertTrue(resultado);
        assertEquals(150.0, contaPoupanca.getSaldo());
    }

    @Test
    void testDepositarValorZero() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.depositar(0.0);

        assertFalse(resultado);
        assertEquals(100.0, contaPoupanca.getSaldo());
    }

    @Test
    void testDepositarValorNegativo() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.depositar(-50.0);

        assertFalse(resultado);
        assertEquals(100.0, contaPoupanca.getSaldo());
    }

    @Test
    void testSacarValorValido() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.sacar(50.0);

        assertTrue(resultado);
        assertEquals(50.0, contaPoupanca.getSaldo());
    }

    @Test
    void testSacarValorIgualAoSaldo() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.sacar(100.0);

        assertTrue(resultado);
        assertEquals(0.0, contaPoupanca.getSaldo());
    }

    @Test
    void testSacarValorMaiorQueSaldo() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.sacar(150.0);

        assertFalse(resultado);
        assertEquals(100.0, contaPoupanca.getSaldo());
    }

    @Test
    void testSacarValorZero() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.sacar(0.0);

        assertFalse(resultado);
        assertEquals(100.0, contaPoupanca.getSaldo());
    }

    @Test
    void testSacarValorNegativo() {
        contaPoupanca.setSaldo(100.0);
        boolean resultado = contaPoupanca.sacar(-50.0);

        assertFalse(resultado);
        assertEquals(100.0, contaPoupanca.getSaldo());
    }

    @Test
    void testGettersESetters() {
        contaPoupanca.setId(1);
        contaPoupanca.setContaId(5);
        contaPoupanca.setClienteId(10);
        contaPoupanca.setNumeroConta("20001");
        contaPoupanca.setSaldo(500.0);
        Date data = new Date();
        contaPoupanca.setDataCriacao(data);

        assertEquals(1, contaPoupanca.getId());
        assertEquals(5, contaPoupanca.getContaId());
        assertEquals(10, contaPoupanca.getClienteId());
        assertEquals("20001", contaPoupanca.getNumeroConta());
        assertEquals(500.0, contaPoupanca.getSaldo());
        assertEquals(data, contaPoupanca.getDataCriacao());
    }

    @Test
    void testHerancaDeConta() {
        // Verificar que ContaPoupanca é uma instância de Conta
        assertTrue(contaPoupanca instanceof Conta);
    }

    @Test
    void testPolimorfismoDepositar() {
        Conta contaPolimorfica = new ContaPoupanca();
        boolean resultado = contaPolimorfica.depositar(100.0);

        assertTrue(resultado);
        assertEquals(100.0, contaPolimorfica.getSaldo());
    }

    @Test
    void testPolimorfismoSacar() {
        Conta contaPolimorfica = new ContaPoupanca();
        contaPolimorfica.setSaldo(100.0);
        boolean resultado = contaPolimorfica.sacar(50.0);

        assertTrue(resultado);
        assertEquals(50.0, contaPolimorfica.getSaldo());
    }

    @Test
    void testContaIdEspecifico() {
        contaPoupanca.setContaId(7);
        assertEquals(7, contaPoupanca.getContaId());
    }

    @Test
    void testMultiplosDepositos() {
        contaPoupanca.setSaldo(0.0);
        contaPoupanca.depositar(100.0);
        contaPoupanca.depositar(50.0);
        contaPoupanca.depositar(25.0);

        assertEquals(175.0, contaPoupanca.getSaldo());
    }

    @Test
    void testMultiplosSaques() {
        contaPoupanca.setSaldo(200.0);
        contaPoupanca.sacar(50.0);
        contaPoupanca.sacar(30.0);
        contaPoupanca.sacar(20.0);

        assertEquals(100.0, contaPoupanca.getSaldo());
    }

    @Test
    void testSequenciaDepositoSaque() {
        contaPoupanca.setSaldo(0.0);
        contaPoupanca.depositar(200.0);
        contaPoupanca.sacar(50.0);
        contaPoupanca.depositar(100.0);
        contaPoupanca.sacar(75.0);

        assertEquals(175.0, contaPoupanca.getSaldo());
    }

    @Test
    void testEncapsulamentoContaId() {
        // Teste específico para o atributo contaId da ContaPoupanca
        contaPoupanca.setContaId(15);
        int contaId = contaPoupanca.getContaId();
        assertEquals(15, contaId);
    }
}

