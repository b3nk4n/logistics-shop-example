import React, { Component } from 'react';

import StockList from './components/stockList';

export default class App extends Component {

    state = {
        items: []
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/stock')
            .then(result => result.json())
            .then(data => {
                this.setState({ items: data })
            })
            .catch(console.log);
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <StockList items={this.state.items} />
                    </div>
                </div>
            </div>
        );
    }
}
