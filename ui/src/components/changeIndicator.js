import PropTypes from 'prop-types';
import React from 'react';

import IndicatorIcon, { IconType } from './indicatorIcon';

import './changeIndicator.css';

export default function ChangeIndicator({ change }) {
    const iconType = getIconType(change);
    return (
        <div>
            <IndicatorIcon className="icon" iconType={iconType} />
            <span className="value">{Math.abs(change)}</span>
        </div>
    );
}

function getIconType(change) {
    if (change < 0) return IconType.decrease;
    if (change > 0) return IconType.increase;
    return IconType.default;
}

ChangeIndicator.propTypes = {
    change: PropTypes.number.isRequired
};