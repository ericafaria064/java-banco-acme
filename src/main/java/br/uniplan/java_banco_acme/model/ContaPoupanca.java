/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: ContaPoupanca
 */

package br.uniplan.java_banco_acme.model;

/**
 * Classe que representa uma Conta Poupança
 * Demonstra herança ao estender a classe Conta
 * Demonstra polimorfismo ao implementar os métodos abstratos
 */
public class ContaPoupanca extends Conta {
    
    private int contaId; // ID da conta corrente associada
    
    public ContaPoupanca() {
        super();
    }
    
    public ContaPoupanca(int contaId, int clienteId, String numeroPoupanca) {
        super(clienteId, numeroPoupanca);
        this.contaId = contaId;
    }
    
    /**
     * Realiza um depósito na conta poupança
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
     * Realiza um saque na conta poupança
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
    
    // Getter e Setter para contaId (Encapsulamento)
    public int getContaId() {
        return contaId;
    }
    
    public void setContaId(int contaId) {
        this.contaId = contaId;
    }
}

