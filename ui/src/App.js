import React, {useEffect, useState} from 'react';

import SearchInput from './components/searchInput';
import StockList from './components/stockList';
import Button from './components/button';

import './App.css';


const debounceDelay = 500;

export default function App() {
    const [items, setItems] = useState(null);
    const [skuQuery, setSkuQuery] = useState('');

    const refresh = () => {
        if (skuQuery.length === 0) {
            doFetch(skuQuery, data => setItems(data))
        } else {
            // empty the query, and let useEffect do it's job
            setSkuQuery('');
        }
    }

    useEffect(() => {
        const handle = setTimeout(() => doFetch(skuQuery, data => setItems(data)), debounceDelay);
        return () => clearTimeout(handle);
    }, [skuQuery]);

    return (
        <div className="container">
            <h1>Shop Item Stocks</h1>
            <div className="row">
                <div className="col-md-4">
                    <Button title="Refresh" onClick={refresh} />
                </div>
                <div className="col-md-4 offset-md-4 search">
                    <SearchInput value={skuQuery} placeholder="Search SKU..." onChange={value => setSkuQuery(value)}/>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <StockList items={items} />
                </div>
            </div>
        </div>
    );
}

function doFetch(skuQuery, onLoaded) {
    if (skuQuery) {
        fetchBySku(skuQuery, onLoaded);
    } else {
        fetchAll(onLoaded);
    }
}

function fetchAll(onLoaded) {
    fetch('http://localhost:8080/api/stock')
        .then(result => {
            if (!result.ok) {
                throw new Error("Non 200 status code.")
            }
            return result.json();
        })
        .then(data => {
            onLoaded(data);
        })
        .catch(console.log);
}

function fetchBySku(skuQuery, onLoaded) {
    fetch('http://localhost:8080/api/stock/' + skuQuery)
        .then(result => {
            if (!result.ok) {
                throw new Error("Non 200 status code or an expected 404 if not found.")
            }
            return result.json();
        })
        .then(data => {
            onLoaded([data]);
        })
        .catch(e => {
            onLoaded([]);
        });
}
