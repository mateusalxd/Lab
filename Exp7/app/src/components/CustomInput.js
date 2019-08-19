import React, { Component } from 'react';

class CustomInput extends Component {

    constructor() {
        super();
        this.state = { value: '' }
        this.setValue = this.setValue.bind(this);
    }

    setValue(event) {
        this.setState({ value: event.target.value })
    }

    render() {
        return (
            <div className="form-group row">
                <label className="col-sm-2 col-form-label" htmlFor={this.props.id}>{this.props.label}</label>
                <div className="col-sm-10">
                    <input className="form-control" {...this.props} onInput={this.setValue} />
                </div>
            </div>
        );
    }

}

export default CustomInput;