# Sistema de Matrícula - Java JPA Hibernate ManyToMany

Este projeto demonstra um sistema de matrícula acadêmica utilizando Java, JPA e Hibernate, com relacionamento ManyToMany entre as entidades **Aluno** e **Curso**.

## Funcionalidades

- Cadastro, listagem e exclusão de alunos e cursos
- Matrícula e cancelamento de matrícula de alunos em cursos
- Listagem de cursos com seus respectivos alunos
- Validações de integridade e mensagens amigáveis ao usuário

## Tecnologias Utilizadas

- Java 21
- Hibernate ORM 6.x
- JPA
- MySQL
- Maven

## Estrutura do Projeto

- `entity/` - Entidades JPA (Aluno, Curso)
- `repository/` - Repositórios genéricos e específicos
- `service/` - Regras de negócio e operações de matrícula
- `app/` - Classe principal de execução
- `util/` - Utilitários (Hibernate, entrada de dados)

## Como Executar

1. Configure o banco de dados MySQL e ajuste o arquivo `hibernate.cfg.xml` conforme necessário.
2. Compile o projeto com Maven.
3. Execute a classe principal:  
   `AppMatricula.java`

## Observações

- O sistema utiliza validações para evitar duplicidade de CPF e nomes de cursos.
- Não é possível excluir alunos matriculados em cursos ou cursos com alunos matriculados.
- O relacionamento ManyToMany é gerenciado via tabela de junção `TB_Matricula`.

---
