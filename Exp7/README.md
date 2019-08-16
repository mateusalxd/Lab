# Experiência 7

## Descrição

API para manutenção de Pessoas.

## Itens relacionados

- Node.js
- GraphQL
- MongoDB

## Como testar

Para iniciar o `MongoDB`, através do diretório `docker`, execute:

```sh
docker-compose up
```

Feito isso execute os comandos para iniciar o servidor (configurado para [localhost:4000/graphql](http://localhost:4000/graphql):

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

A documentação das `Queries` e da `Mutation` estão disponíveis no canto superior direito, através do botão `< Docs`.
