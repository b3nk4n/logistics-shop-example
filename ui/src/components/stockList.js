import React from 'react';

import IndicatorIcon, { IconType } from "./indicatorIcon";

export default function StockList({ items }) {
    return (
        <div>
            <h1>Contact List</h1>
            {items.map((item) => (
                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">{item.sku}</h5>
                        <h6 className="card-subtitle mb-2 text-muted">{item.amount}</h6>
                        <p className="card-text">{item.change}</p>
                        <IndicatorIcon iconType={IconType.increase} />
                        <IndicatorIcon iconType={IconType.decrease} />
                        <IndicatorIcon iconType={IconType.default} />
                    </div>
                </div>
            ))}
        </div>
    );
}