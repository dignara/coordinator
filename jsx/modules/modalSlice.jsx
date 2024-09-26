import {createSlice} from '@reduxjs/toolkit'

const GOODS_BLANK = {id: 0, category: undefined, brand: undefined, price: undefined}
const INIT_GOODS = {show: false, goods: GOODS_BLANK, callback: undefined}
const INIT_LOGIN = {show: false, callback: undefined}
export const slice = createSlice({
    name        : 'modal',
    initialState: {
        loginModal: INIT_GOODS,
        goodsModal: INIT_LOGIN
    },
    reducers    : {
        showGoodsModal: (state, {payload}) => {
            state.goodsModal = {
                show    : true,
                goods   : payload?.goods ? payload.goods : GOODS_BLANK,
                callback: payload?.callback
            }
        },
        hideGoodsModal: (state, {payload}) => {
            if (state.goodsModal.callback) {
                state.goodsModal.callback(payload)
            }
            state.goodsModal = INIT_GOODS
        },
        showLoginModal: (state, {payload}) => {
            state.loginModal = {
                show    : true,
                callback: payload?.callback
            }
        },
        hideLoginModal: (state, {payload}) => {
            if (state.loginModal.callback) {
                state.loginModal.callback(payload)
            }
            state.loginModal = INIT_LOGIN
        }
    }
})

export const {showLoginModal, hideLoginModal, showGoodsModal, hideGoodsModal} = slice.actions

export const getLoginModal = state => state.modal.loginModal
export const getGoodsModal = state => state.modal.goodsModal

export default slice.reducer