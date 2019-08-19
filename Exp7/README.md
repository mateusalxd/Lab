# Experiência 7

## Descrição

API para manutenção de Pessoas.

## Itens relacionados

- Node.js
- GraphQL
- MongoDB
- React

## Como testar

Para iniciar o `MongoDB`, através do diretório `docker`, execute:

```sh
docker-compose up
```

Feito isso execute os comandos para iniciar o servidor a partir do diretório `api` (configurado para [localhost:4000/graphql](http://localhost:4000/graphql)):

```sh
npm install
npm start
```

Para inserir uma nova Pessoa, execute a `mutation` abaixo:

```graphql
mutation {
    pessoa(nome: "Mateus", cpf: "12345698765") {
        id
        nome
    }
}
```

Para remover uma nova Pessoa, execute a `mutation` abaixo:

```graphql
mutation {
    removerPessoa(id: "5d5a8fe7479fb91b42dca0fe")
}
```

**Observação**: utilize um `id` existente.

Para realizar uma consulta, execute a `query` abaixo:

```graphql
query {
    pessoas {
        id
        nome
        cpf
    }
}
```

Ou

```graphql
query {
    pessoa(id: "5d55b3c6cdf7df73cee452f0") {
        nome
        cpf
    }
}
```

**Observação**: na consulta por `id`, utilize um `id` recuperado através da primeira `query`.

A documentação das `Queries` e das `Mutations` estão disponíveis no canto superior direito, através do botão `< Docs`.

Para consumir a API através de uma aplicação em `React`, execute os comandos abaixo no diretório `app` (configurado para [localhost:3000](http://localhost:3000)):

```sh
npm install
npm start
```

Para remover uma pessoa, dê um clique duplo na linha da tabela.

## Anotações

- [ENOSPC: System limit for number of file watchers reached](https://github.com/guard/listen/wiki/Increasing-the-amount-of-inotify-watchers#the-technical-details)
