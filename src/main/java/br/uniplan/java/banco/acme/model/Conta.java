/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: Conta
 */

package br.uniplan.java.banco.acme.model;

import java.util.Date;

/**
 * Classe abstrata que representa uma Conta bancária
 * Demonstra abstração e será usada como base para herança
 */
public abstract class Conta {
    
    protected int id;
    protected int clienteId;
    protected String numeroConta;
    protected double saldo;
    protected Date dataCriacao;
    
    // Construtor
    public Conta() {
        this.saldo = 0.0;
        this.dataCriacao = new Date();
    }
    
    public Conta(int clienteId, String numeroConta) {
        this.clienteId = clienteId;
        this.numeroConta = numeroConta;
        this.saldo = 0.0;
        this.dataCriacao = new Date();
    }
    
    // Métodos abstratos (Abstração)
    public abstract boolean depositar(double valor);
    public abstract boolean sacar(double valor);
    
    // Getters e Setters (Encapsulamento)
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getNumeroConta() {
        return numeroConta;
    }
    
    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public Date getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    @Override
    public String toString() {
        return "Conta{" + "id=" + id + ", numeroConta=" + numeroConta + ", saldo=" + saldo + '}';
    }
}

