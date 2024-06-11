# Sistema de Gerenciamento de Biblioteca

## Visão Geral

O Sistema de Gerenciamento de Biblioteca é uma aplicação Java que permite aos usuários gerenciar uma coleção de livros. Ele suporta operações como listar livros disponíveis, registrar novos livros, alugar livros e devolver livros. A aplicação utiliza JSON para armazenamento de dados e comunica-se pela rede utilizando sockets.

## Funcionalidades

- Listar todos os livros na coleção
- Registrar novos livros
- Alugar livros
- Devolver livros
- Atualizar o número de cópias de um livro específico

## Estrutura do Projeto

- **Book.java**: Representa um livro com atributos como autor, título, gênero e cópias.
- **BookCollection.java**: Gerencia a coleção de livros, incluindo adicionar, remover e atualizar livros.
- **ClientHandler.java**: Manipula as requisições dos clientes e processa comandos como listar, registrar, alugar, devolver e atualizar.
- **JsonUtil.java**: Classe utilitária para ler e escrever arquivos JSON.
- **LibraryClient.java**: Aplicação cliente que se conecta ao servidor da biblioteca, permitindo aos usuários enviar comandos e receber respostas.
- **LibraryServer.java**: Aplicação servidor que gerencia a coleção de livros e manipula as conexões dos clientes.
- **books.json**: Arquivo JSON que armazena a coleção de livros.

## Como Executar

### Pré-requisitos

- Java Development Kit (JDK) 11 ou superior
- Maven


### Comandos Disponíveis

- **list**: Lista todos os livros na coleção.
- **register,author,title,genre,copies**: Registra um novo livro com os detalhes especificados.
- **rent,title**: Aluga um livro com o título especificado.
- **return,title**: Devolve um livro com o título especificado.
- **title,newCopies**: Atualiza o número de cópias de um livro com o título especificado.
- **exit**: Sai da aplicação cliente.

## Exemplo

1. **Listar todos os livros:**

   ```sh
   Digite o comando (list, register, rent, return, exit): list
   ```

2. **Registrar um novo livro:**

   ```sh
   Digite o comando (list, register, rent, return, exit): register
   Digite os detalhes (autor,título,gênero,cópias): "John Doe","Livro Exemplo","Gênero Exemplo",5
   ```

3. **Alugar um livro:**

   ```sh
   Digite o comando (list, register, rent, return, exit): rent
   Digite o título: "Livro Exemplo"
   ```

4. **Devolver um livro:**

   ```sh
   Digite o comando (list, register, rent, return, exit): return
   Digite o título: "Livro Exemplo"
   ```

## Licença

Este projeto é licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.

## Bibliotecas

- Este projeto utiliza a biblioteca Jackson para processamento de JSON.

---
