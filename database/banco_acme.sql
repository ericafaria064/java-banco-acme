-- Curso: Análise e Desenvolvimento de Sistemas
-- Aluno: Erica Irdes de Faria
-- Disciplina: POO II
-- Script de criação do banco de dados Banco ACME

CREATE DATABASE IF NOT EXISTS banco_acme;
USE banco_acme;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(100),
    telefone VARCHAR(20),
    endereco VARCHAR(200),
    data_cadastro DATE NOT NULL
);

-- Tabela de Contas
CREATE TABLE IF NOT EXISTS contas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    numero_conta VARCHAR(20) NOT NULL UNIQUE,
    saldo DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    data_criacao DATE NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

-- Tabela de Contas Poupança
CREATE TABLE IF NOT EXISTS contas_poupanca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conta_id INT NOT NULL,
    numero_poupanca VARCHAR(20) NOT NULL UNIQUE,
    saldo DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    data_criacao DATE NOT NULL,
    FOREIGN KEY (conta_id) REFERENCES contas(id) ON DELETE CASCADE
);

-- Tabela de Transações (Depósitos e Saques)
CREATE TABLE IF NOT EXISTS transacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conta_id INT,
    conta_poupanca_id INT,
    tipo VARCHAR(20) NOT NULL, -- 'DEPOSITO', 'SAQUE', 'DEPOSITO_POUPANCA', 'SAQUE_POUPANCA'
    valor DECIMAL(10, 2) NOT NULL,
    data_transacao DATETIME NOT NULL,
    FOREIGN KEY (conta_id) REFERENCES contas(id) ON DELETE CASCADE,
    FOREIGN KEY (conta_poupanca_id) REFERENCES contas_poupanca(id) ON DELETE CASCADE
);

