import PropTypes from 'prop-types';
import React from 'react';

export default function Button({ title, onClick }) {
    return (
        <button type="button" className="btn btn-dark" onClick={onClick}>
            {title}
        </button>
    );
}

Button.propTypes = {
    title: PropTypes.string.isRequired,
    onClick: PropTypes.func
}