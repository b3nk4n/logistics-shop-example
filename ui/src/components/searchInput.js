import PropTypes from 'prop-types';
import React from 'react';

export default function SearchInput({ placeholder, onSearch}) {
    return (
        <div className="input-group">
            <input className="form-control py-2 border-right-0 border"
                   type="search"
                   placeholder={placeholder}
                   id="example-search-input"
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
    placeholder: PropTypes.string.isRequired,
    onSearch: PropTypes.func.isRequired
};