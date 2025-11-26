/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaCorrente
 */

package br.uniplan.java_banco_acme.model;

/**
 * Classe que representa uma Conta Corrente
 * Demonstra herança ao estender a classe Conta
 * Demonstra polimorfismo ao implementar os métodos abstratos
 */
public class ContaCorrente extends Conta {
    
    public ContaCorrente() {
        super();
    }
    
    public ContaCorrente(int clienteId, String numeroConta) {
        super(clienteId, numeroConta);
    }
    
    /**
     * Realiza um depósito na conta corrente
     * @param valor valor a ser depositado
     * @return true se o depósito foi realizado com sucesso
     */
    @Override
    public boolean depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }
    
    /**
     * Realiza um saque na conta corrente
     * @param valor valor a ser sacado
     * @return true se o saque foi realizado com sucesso
     */
    @Override
    public boolean sacar(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }
}

