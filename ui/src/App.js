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
            <StockList items={this.state.items} />
        );
    }
}
