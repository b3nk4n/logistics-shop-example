import PropTypes from 'prop-types';
import React from 'react';

import StockListItem from './stockListItem';

import './stockList.css';

export default function StockList({ items }) {
    return (
        <>
            {items.map((item, index) => (
                <StockListItem key={index} item={item}/>
            ))}
        </>
    );
}

StockList.propTypes = {
    items: PropTypes.arrayOf(PropTypes.shape({
        sku: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        amount: PropTypes.number.isRequired,
        change: PropTypes.number.isRequired
    }))
};