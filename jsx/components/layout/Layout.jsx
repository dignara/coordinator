import React from "react"

export const loadingLayout = (title, message) => {
    return (
        <div className='text-center mt-5'>
            <div className='spinner-border mb-3'>
                <span className='visually-hidden'>Loading...</span>
            </div>
            <h3 className='text-uppercase text-danger mt-3'>{title}</h3>
            <p className='text-muted mt-3'>{message}</p>
        </div>
    )
}

export const errorLayout = (title, message) => {
    return (
        <div className='text-center mt-5'>
            <i className='fs-1 bi bi-window-x'/>
            <h3 className='text-uppercase text-danger mt-3'>{title}</h3>
            <p className='text-muted mt-3'>{message}</p>
        </div>
    )
}