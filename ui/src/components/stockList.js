import PropTypes from 'prop-types';
import React from 'react';

import StockListItem from './stockListItem';

export default function StockList({ items }) {
    return (
        <div>
            <h1>Shop Item Stocks</h1>
            {items.map((item) => (
                <StockListItem item={item} />
            ))}
        </div>
    );
}

StockList.propTypes = {
    items: PropTypes.arrayOf(PropTypes.shape({
        sku: PropTypes.string,
        title: PropTypes.string,
        amount: PropTypes.number,
        change: PropTypes.number
    }))
};