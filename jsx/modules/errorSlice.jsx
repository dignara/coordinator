import {createSlice} from '@reduxjs/toolkit'

export const slice = createSlice({
    name: 'error',
    initialState: {
        auth: false,
    },
    reducers: {
        setAuthError: (state, {payload}) => {
            state.auth = payload
        }
    }
})

export const {setAuthError} = slice.actions

export const getAuthError = state => state.error.auth

export default slice.reducer