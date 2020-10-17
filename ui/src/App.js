import React, { Component } from 'react';

import SearchInput from './components/searchInput';
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
                <h1>Shop Item Stocks</h1>
                <div className="row">
                    <div className="col-md-4 offset-md-8 search">
                        <SearchInput placeholder="Search SKU..." onSearch={value => console.log(value)}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col">
                        <StockList items={this.state.items} />
                    </div>
                </div>
            </div>
        );
    }
}
