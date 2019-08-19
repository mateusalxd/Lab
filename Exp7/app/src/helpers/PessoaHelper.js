class PessoaHelper {

    static prepareInsertMutation(form) {
        const query = `
        mutation adicionarPessoa($nome: String!, $cpf: String!) {
            pessoa(nome: $nome, cpf: $cpf) {
              id
            }
          }
        `;

        const variables = {
            nome: form.nome.value,
            cpf: form.cpf.value
        };

        return JSON.stringify({ query, variables });
    }

    static prepareDeleteMutation(id) {
        const query = `
        mutation removerPessoa($id: ID!) {
            removerPessoa(id: $id)
          }
        `;

        const variables = {
            id
        };

        return JSON.stringify({ query, variables });
    }

    static prepareQuery() {
        const query = `
        query {
            pessoas {
                id
                nome
                cpf
            }
        }
        `;

        return JSON.stringify({ query, variables: {} });
    }

}

export default PessoaHelper;