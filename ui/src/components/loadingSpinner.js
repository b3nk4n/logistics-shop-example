import React from 'react';

export default function LoadingSpinner() {
    return (
        <div className="spinner-border m-5" role="status">
            <span className="sr-only">Loading...</span>
        </div>
    );
}