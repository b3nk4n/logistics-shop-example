import PropTypes from 'prop-types';
import React from 'react';

import ChangeIndicator from './changeIndicator';

import './stockListItem.css';

export default function StockListItem({ sku, title, amount, change }) {
    return (
        <div className="card">
            <div className="card-body item-body">
                <div className="container">
                    <div className="row">
                        <div className="col-9">
                            <h5 className="card-title">{title}</h5>
                            <p className="card-text">{`SKU: ${sku}`}</p>
                        </div>
                        <div className="col-3 stats">
                            <span>In Stock:</span>{' '}<strong>{amount}</strong>
                            <ChangeIndicator change={change} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

StockListItem.propTypes = {
    sku: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    amount: PropTypes.number.isRequired,
    change: PropTypes.number.isRequired
};