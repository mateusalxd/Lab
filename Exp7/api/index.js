const express = require('express')
const graphqlHTTP = require('express-graphql');
const schema = require('./graphql/schema');

var app = express();
app.use('/graphql',
    graphqlHTTP({
        schema,
        graphiql: true
    }));
app.listen(4000, () => {
    console.log('Exeuctando em localhost:4000/graphql')
})
