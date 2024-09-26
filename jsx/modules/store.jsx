import {configureStore} from "@reduxjs/toolkit";
import errorReducer from './errorSlice'
import statusReducer from './statusSlice'
import modalReducer from './modalSlice'

export default configureStore({
    reducer: {
        modal: modalReducer,
        status: statusReducer,
        error: errorReducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware( {
        serializableCheck: false
    })
})