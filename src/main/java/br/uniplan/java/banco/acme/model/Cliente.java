/*
 * Curso: Análise e Desenvolvimento de Sistemas
 * Aluno: Erica Irdes de Faria
 * Disciplina: POO II
 * Classe: Cliente
 */

package br.uniplan.java.banco.acme.model;

import java.util.Date;

/**
 * Classe que representa um Cliente do banco
 * Demonstra encapsulamento através dos atributos privados e métodos getters/setters
 */
public class Cliente {
    
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private Date dataCadastro;
    
    // Construtores
    public Cliente() {
    }
    
    public Cliente(String nome, String cpf, String email, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = new Date();
    }
    
    // Getters e Setters (Encapsulamento)
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public Date getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + '}';
    }
}

