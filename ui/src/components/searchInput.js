import PropTypes from 'prop-types';
import React from 'react';

export default function SearchInput({ value, placeholder, onChange}) {
    return (
        <div className="input-group">
            <input className="form-control py-2 border-right-0 border"
                   type="search"
                   value={value}
                   placeholder={placeholder}
                   id="example-search-input"
                   onChange={event => onChange(event.target.value)}
            />
            <span className="input-group-append">
                <div className="input-group-text bg-transparent">
                    <i className="fa fa-search" />
                </div>
            </span>
        </div>
    );
}

SearchInput.propTypes = {
    value: PropTypes.string.isRequired,
    placeholder: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired
};