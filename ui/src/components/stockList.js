import PropTypes from 'prop-types';
import React from 'react';

import LoadingSpinner from './loadingSpinner';
import StockListItem from './stockListItem';

import './stockList.css';

export default function StockList({ items }) {
    if (!items) {
        return (
            <div className="d-flex justify-content-center">
                <LoadingSpinner />
            </div>
        );
    }

    return (
        <>
            {items.map((item, index) => (
                <StockListItem
                    key={index}
                    sku={item.sku}
                    title={item.title}
                    amount={item.amount}
                    change={item.change}
                />
            ))}
            {items.length === 0 && (
                <div className="empty-results">
                    <span>No items found.</span>
                </div>

            )}
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