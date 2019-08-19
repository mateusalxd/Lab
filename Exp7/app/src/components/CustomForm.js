import React, { Component } from 'react';
import CustomInput from './CustomInput';
import CustomSubmitButton from './CustomSubmitButton';
import PessoaHelper from '../helpers/PessoaHelper';
import CustomList from './CustomList';

class CustomForm extends Component {

    constructor() {
        super();
        this.state = { list: [] }
        this._postData = this._postData.bind(this);
        this._queryData = this._queryData.bind(this);
        this._deleteData = this._deleteData.bind(this);
    }

    _clearFields(form) {
        form.nome.value = '';
        form.cpf.value = '';
        form.nome.focus();
    }

    _fetch(body) {
        return fetch("http://localhost:4000/graphql", {
            method: "POST",
            body,
            headers: { "Content-Type": "application/json" }
        });
    }

    _deleteData(event) {
        const id = event.target.parentNode.getAttribute("id");
        
        this._fetch(PessoaHelper.prepareDeleteMutation(id))
            .then(() => {
                this._queryData();
            }).catch(err => {
                console.log(err);
            });
    }

    _postData(event) {
        event.preventDefault();
        const form = event.target;

        this._fetch(PessoaHelper.prepareInsertMutation(form))
            .then(() => {
                this._clearFields(form);
                this._queryData();
            }).catch(err => {
                console.log(err);
            });
    }

    _queryData() {
        this._fetch(PessoaHelper.prepareQuery())
            .then(response => {
                response.json().then(resp => this.setState({ list: resp.data.pessoas }));
            }).catch(err => {
                console.log(err);
            });
    }

    componentDidMount() {
        this._queryData();
    }

    render() {
        return (
            <>
                <div className="border rounded" style={{ padding: 2 + "em" }}>
                    <form onSubmit={this._postData}>
                        <CustomInput id="nome" type="text" label="Nome" />
                        <CustomInput id="cpf" type="number" label="CPF" />
                        <CustomSubmitButton />
                    </form>
                </div>
                <div style={{ paddingTop: 2 + "em" }}>
                    <CustomList data={this.state.list} removeItem={this._deleteData} />
                </div>
            </>
        );
    }

}

export default CustomForm