import React, { Component } from 'react';

class CustomList extends Component {

    constructor() {
        super()
        this._removeItem = this._removeItem.bind(this);
    }

    _removeItem(event) {
        this.props.removeItem(event);
    }

    render() {
        return (
            <>
                <table className="table table-hover">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">Nome</th>
                            <th scope="col">CPF</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.props.data.map(person => {
                            return <tr key={person.id} id={person.id} onDoubleClick={this._removeItem}>
                                <td>{person.nome}</td>
                                <td>{person.cpf}</td>
                            </tr>
                        })}
                    </tbody>
                </table>
            </>
        );
    }

}

export default CustomList;