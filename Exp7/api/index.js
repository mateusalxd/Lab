const express = require('express')
const graphqlHTTP = require('express-graphql');
const schema = require('./graphql/schema');
const cors = require('cors');

var app = express();
app.use(cors());
app.use('/graphql',
    graphqlHTTP({
        schema,
        graphiql: true
    }));
app.listen(4000, () => {
    console.log('Exeuctando em http://localhost:4000/graphql');
});
