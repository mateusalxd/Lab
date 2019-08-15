const {
    GraphQLID,
    GraphQLString,
    GraphQLNonNull,
    GraphQLObjectType
} = require("graphql");

const TipoPessoa = new GraphQLObjectType({
    name: "Pessoa",
    fields: {
        id: { type: GraphQLNonNull(GraphQLID) },
        nome: { type: GraphQLNonNull(GraphQLString) },
        cpf: { type: GraphQLNonNull(GraphQLString) }
    }
});

module.exports = TipoPessoa

