# Sistema Banco ACME

Sistema bancário desenvolvido em Java Swing com conexão MySQL para a disciplina de POO II.

## Informações do Projeto

- **Curso:** Análise e Desenvolvimento de Sistemas
- **Aluno:** Erica Irdes de Faria
- **Disciplina:** POO II

## Requisitos

- Java 17 ou superior
- MySQL 8.0 ou superior
- Gradle (opcional, pode usar IDE como NetBeans)

## Configuração do Banco de Dados

1. Execute o script SQL localizado em `database/banco_acme.sql` no MySQL para criar o banco de dados e as tabelas.

2. Configure as credenciais de conexão no arquivo `src/main/java/br/uniplan/java/banco/acme/util/ConexaoMySQL.java`:
   - URL: `jdbc:mysql://localhost:3306/banco_acme`
   - Usuário: `root` (ou seu usuário MySQL)
   - Senha: (configure sua senha MySQL)

## Como Executar

### Usando NetBeans:

1. Abra o projeto no NetBeans
2. Configure o banco de dados MySQL conforme descrito acima
3. Execute a classe `TelaPrincipal` localizada em `src/main/java/br/uniplan/java/banco/acme/view/TelaPrincipal.java`

### Usando linha de comando:

```bash
./gradlew build
./gradlew run
```

## Funcionalidades

1. **Cadastrar Cliente** - Cadastra novos clientes no sistema
2. **Criar Conta** - Cria uma conta corrente para um cliente
3. **Criar Conta Poupança** - Cria uma conta poupança (requer conta corrente existente)
4. **Depositar** - Realiza depósitos em contas correntes ou poupança
5. **Sacar** - Realiza saques de contas correntes ou poupança

## Estrutura do Projeto

```
src/main/java/br/uniplan/java/banco/acme/
├── dao/              # Classes de acesso a dados
├── model/            # Classes de modelo (Cliente, Conta, etc.)
├── util/             # Classes utilitárias (ConexaoMySQL)
└── view/             # Interfaces Swing
```

## Conceitos de POO Utilizados

- **Encapsulamento:** Atributos privados com getters/setters
- **Herança:** ContaCorrente e ContaPoupanca herdam de Conta
- **Polimorfismo:** Métodos depositar() e sacar() implementados de forma diferente em cada classe
- **Abstração:** Classe Conta é abstrata com métodos abstratos
- **Atributos e Métodos:** Todas as classes possuem atributos e métodos apropriados
