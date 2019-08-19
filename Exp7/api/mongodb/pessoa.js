const mongoose = require('mongoose')
mongoose.set('useFindAndModify', false);

mongoose.connect("mongodb://admin:admin@localhost/pessoa?authSource=admin", { useNewUrlParser: true })
    .then(retorno => {
        console.log('Conectado ao MongoDB');
    })
    .catch(erro => {
        console.log(erro)
    });

const Pessoa = mongoose.model("pessoa", {
    nome: String,
    cpf: String
});

module.exports = Pessoa