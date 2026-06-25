# Cadastro de Clientes

Aplicação desktop desenvolvida em Java Swing para cadastro, busca e remoção de clientes utilizando uma interface gráfica simples e intuitiva.

## Funcionalidades

- Cadastro de clientes
- Validação de campos obrigatórios
- Busca de clientes por nome
- Exclusão de clientes selecionados
- Exibição dos registros em tabela
- Seleção de sexo (Masculino/Feminino)
- Atualização automática da tabela após inserções e remoções

## Tecnologias Utilizadas

- Java
- Java Swing
- JTable
- AbstractTableModel
- Programação Orientada a Objetos (POO)

## Estrutura do Projeto

```
CadastroDeClientes/
│
├── src/
│   ├── model/
│   │   ├── Cliente.java
│   │   └── ClienteTableModel.java
│   │
│   └── view/
│       └── TelaCadastro.java
│
└── img/
    └── cadastro.png
```

## Classes Principais

### Cliente

Classe responsável por representar um cliente do sistema.

Atributos:

- Nome
- Telefone
- E-mail
- Sexo

### ClienteTableModel

Classe que herda de `AbstractTableModel` e realiza a comunicação entre a lista de clientes e a tabela exibida na interface.

Responsabilidades:

- Inserir clientes
- Remover clientes
- Buscar clientes
- Atualizar a JTable automaticamente

### TelaCadastro

Janela principal da aplicação.

Responsável por:

- Exibir os componentes gráficos
- Receber os dados do usuário
- Acionar as operações de cadastro, busca e exclusão
- Atualizar a tabela de clientes

## Como Executar

### Pré-requisitos

- Java JDK 17 ou superior

Verifique a instalação:

```bash
java --version
javac --version
```

### Compilação

Na pasta do projeto:

```bash
javac -d bin src/model/*.java src/view/*.java
```

### Execução

```bash
java -cp bin view.TelaCadastro
```

## Exemplo de Uso

1. Informe o nome do cliente.
2. Informe o telefone.
3. Informe o e-mail.
4. Selecione o sexo.
5. Clique em **Salvar**.
6. O cliente será adicionado à tabela.
7. Para localizar um cliente, informe o nome no campo de busca e clique em **Buscar**.
8. Para remover um cliente, selecione uma linha da tabela e clique em **Excluir**.

## Conceitos Aplicados

- Programação Orientada a Objetos
- Encapsulamento
- Modelagem de dados
- Interface gráfica com Swing
- MVC simplificado
- Manipulação de eventos
- Modelos de tabela personalizados (`AbstractTableModel`)

## Autor

**Lucas Marques da Cunha**

Universidade Federal de Rondônia (UNIR)
