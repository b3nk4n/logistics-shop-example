import PropTypes from 'prop-types';
import React from 'react';

import ChangeIndicator from './changeIndicator';

export default function StockListItem({ item }) {
    return (
        <div className="card">
            <div className="card-body">
                <div className="container">
                    <div className="row">
                        <div className="col-10">
                            <h5 className="card-title">{item.title}</h5>
                            <p className="card-text">{`SKU: ${item.sku}`}</p>
                        </div>
                        <div className="col-2">
                            <span>In Stock:</span>{' '}<strong>{item.amount}</strong>
                            <ChangeIndicator change={item.change} />
                        </div>
                    </div>
                </div>
            </div>
        </div>

        // <div className="card">
        //     <div className="card-body">
        //         <h5 className="card-title">{item.sku}</h5>
        //         <h6 className="card-subtitle mb-2 text-muted">{item.amount}</h6>
        //         <p className="card-text">{item.change}</p>
        //         <IndicatorIcon iconType={IconType.increase} />
        //         <IndicatorIcon iconType={IconType.decrease} />
        //         <IndicatorIcon iconType={IconType.default} />
        //     </div>
        // </div>
    );
}

StockListItem.propTypes = {
    item: PropTypes.shape({
        sku: PropTypes.string,
        title: PropTypes.string,
        amount: PropTypes.number,
        change: PropTypes.number
    })
};