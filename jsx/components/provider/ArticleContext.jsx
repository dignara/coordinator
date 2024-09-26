import {createContext, useMemo, useState} from "react"
import * as React from "react"

export const ArticleContext = createContext({})

export const ArticleProvider = ({children}) => {
    const [state, setState] = useState({category: undefined})

    const actions = useMemo(() => ({
        setCategory: (category) => {
            setState({...state, category})
        },
    }), [state])

    const value = useMemo(() => [state, actions], [state, actions])

    return (
        <ArticleContext.Provider value={value}>{children}</ArticleContext.Provider>
    )
}