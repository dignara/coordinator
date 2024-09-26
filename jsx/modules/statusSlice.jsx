import {createSlice} from '@reduxjs/toolkit'

export const slice = createSlice({
    name        : 'status',
    initialState: {
        user: {isAdmin: false},
    },
    reducers    : {
        setAdmin : (state, {payload}) => {
            state.user = {...state.user, isAdmin: payload}
        }
    }
})

export const {setAdmin} = slice.actions
export const isAdmin = state => state.status.user.isAdmin

export default slice.reducer