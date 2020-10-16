import PropTypes from 'prop-types';
import React from "react";

import './indicatorIcon.css';

export const IconType = Object.freeze({
    default: {
        path: 'M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm2.5 7.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7z',
        colorClass: 'text-secondary'
    },
    increase: {
        path: 'M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm4 9a.5.5 0 0 1-.374-.832l4-4.5a.5.5 0 0 1 .748 0l4 4.5A.5.5 0 0 1 12 11H4z',
        colorClass: 'text-success'
    },
    decrease: {
        path: 'M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm4 4a.5.5 0 0 0-.374.832l4 4.5a.5.5 0 0 0 .748 0l4-4.5A.5.5 0 0 0 12 6H4z',
        colorClass: 'text-danger'
    }
});

export default function IndicatorIcon({ iconType }) {
    return (
        <svg width="1em" height="1em" viewBox="0 0 16 16" className={`bi bi-dash-square-fill icon ${iconType.colorClass}`}
             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" d={iconType.path}/>
        </svg>
    );
}

IndicatorIcon.propTypes = {
    iconType: PropTypes.shape({
        path: PropTypes.string,
        colorClass: PropTypes.string
    })
}

