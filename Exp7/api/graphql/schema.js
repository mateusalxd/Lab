const TipoPessoa = require('./tipoPessoa');
const Pessoa = require('../mongodb/pessoa')
const {
    GraphQLSchema,
    GraphQLObjectType,
    GraphQLList,
    GraphQLString,
    GraphQLNonNull,
    GraphQLID
} = require("graphql");

const schema = new GraphQLSchema({
    query: new GraphQLObjectType({
        name: "Query",
        fields: {
            pessoas: {
                type: GraphQLList(TipoPessoa),
                resolve: (root, args, context, info) => {
                    return Pessoa.find().exec();
                }
            },
            pessoa: {
                type: TipoPessoa,
                args: {
                    id: { type: GraphQLNonNull(GraphQLID) }
                },
                resolve: (root, args, context, info) => {
                    return Pessoa.findById(args.id).exec();
                }
            }
        }
    }),
    mutation: new GraphQLObjectType({
        name: "Mutation",
        fields: {
            pessoa: {
                type: TipoPessoa,
                args: {
                    nome: { type: GraphQLNonNull(GraphQLString) },
                    cpf: { type: GraphQLNonNull(GraphQLString) }
                },
                resolve: (root, args, context, info) => {
                    const pessoa = new Pessoa(args);
                    return pessoa.save();
                }
            }
        }
    })
});

module.exports = schema