import React, {useContext, useState} from "react"
import {Modal} from 'react-bootstrap'
import {useDispatch, useSelector} from "react-redux"
import {hideGoodsModal} from "../../modules/modalSlice"
import {toast} from "react-hot-toast"
import {CATEGORIES} from "../../Constants";
import Select from "react-select";
import {useMutation} from "@apollo/client";
import {
    MUTATION_GOODS_DELETE,
    MUTATION_GOODS_SAVE,
    QUERY_GET_GOODS, QUERY_GET_HIGH_AND_LOW_PRICE_GOODS,
    QUERY_GET_LOW_PRICE_BRAND_GOODS
} from "../query/goods";
import {isAdmin} from "../../modules/statusSlice"
import {ArticleContext} from "../provider/ArticleContext";

const MODE = {
    ADD   : 0,
    EDIT  : 1,
    DETAIL: 2
}

const GoodsModal = ({goods: savedGoods}) => {
    const [{category}] = useContext(ArticleContext)
    const [deleteGoods, {called: deleteCalled, loading: deleteLoading}] = useMutation(MUTATION_GOODS_DELETE);
    const [saveGoods, {called: saveCalled, loading: saveLoading}] = useMutation(MUTATION_GOODS_SAVE);
    const [goods, setGoods] = useState(savedGoods)
    const dispatch = useDispatch()
    const admin = useSelector(isAdmin)
    const mode = admin ? (goods.id === 0 ? MODE.ADD : MODE.EDIT) : MODE.DETAIL
    const isEdit = mode !== MODE.DETAIL

    const closeModal = () => {
        dispatch(hideGoodsModal())
    }

    const isReady = (mode) => {
        switch (mode) {
            case MODE.ADD:
                return isReadyFields()
            case MODE.EDIT:
                return JSON.stringify(savedGoods) !== JSON.stringify(goods) && isReadyFields()
            case MODE.DETAIL:
                return true
        }
    }

    const isReadyFields = () => {
        return goods?.price > 0 && goods?.category && goods?.brand?.length > 0
    }

    const requestSaveGoods = () => {
        saveGoods({
            variables: {
                goodsInput: {
                    id      : goods.id,
                    category: goods.category,
                    brand   : goods.brand,
                    price   : goods.price,
                }
            },
            refetchQueries: [
                {query: QUERY_GET_LOW_PRICE_BRAND_GOODS},
                {query: QUERY_GET_GOODS},
                {query: QUERY_GET_HIGH_AND_LOW_PRICE_GOODS, variables : { category: category}}
            ]
        }).then(response => {
            toast.success(getSuccessMessage(mode))
            closeModal()
        }).catch(error => {
            toast.error(error.message)
        })
    }

    const requestDeleteGoods = () => {
        deleteGoods({
            variables: {
                id: goods.id
            },
            refetchQueries: [
                {query: QUERY_GET_LOW_PRICE_BRAND_GOODS},
                {query: QUERY_GET_GOODS},
                {query: QUERY_GET_HIGH_AND_LOW_PRICE_GOODS, variables : { category: category}}
            ]
        }).then(response => {
            toast.success("상품이 삭제되었습니다.")
            closeModal()
        }).catch(error => {
            toast.error(error.message)
        })
    }

    const onClickButton = (mode) => {
        switch (mode) {
            case MODE.EDIT:
            case MODE.ADD:
                requestSaveGoods()
                break
            case MODE.DETAIL:
                closeModal()
                break
        }
    }

    return (
        <>
            <Modal show={true} onHide={closeModal} backdrop='static' centered>
                <Modal.Header data-bs-theme="dark" closeButton>
                    <Modal.Title>
                        {getTitle(mode)}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="form">
                        <div className="row">
                            <Select
                                isDisabled={!isEdit}
                                value={goods.category ? {value: goods.category, label: goods.category} : undefined}
                                placeholder="카테고리를 선택하세요."
                                onChange={category => setGoods({...goods, category: category.value})}
                                options={CATEGORIES.map(category => ({value: category, label: category}))}
                            />
                        </div>
                        <div className="row mt-2">
                            <div className="col form-group">
                                <input type="text" name="brand" value={goods.brand} disabled={!isEdit}
                                       className="form-control" placeholder="브랜드를 입력하세요." required
                                       onChange={e => setGoods({...goods, brand: e.target.value})}/>
                            </div>
                        </div>
                        <div className="row mt-2">
                            <div className="col form-group">
                                <input type="number" name="price" value={goods.price} disabled={!isEdit}
                                       className="form-control" placeholder="가격을 입력하세요." required
                                       onChange={e => setGoods({...goods, price: e.target.valueAsNumber})}/>
                            </div>
                        </div>
                        <div className="mt-3 submit">
                            <button type="button" className="btn btn-dark w-100" disabled={!isReady(mode)}
                                    onClick={() => onClickButton(mode)}>{getButtonTitle(mode)}</button>
                        </div>
                        {
                            mode === MODE.EDIT &&
                            <div className="mt-2 submit">
                                <button type="button" className="btn btn-danger w-100" disabled={false}
                                        onClick={requestDeleteGoods}>삭제
                                </button>
                            </div>
                        }
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

const getSuccessMessage = (mode) => {
    switch (mode) {
        case MODE.EDIT:
            return "상품 정보가 수정되었습니다."
        case MODE.ADD:
            return "상품이 등록되었습니다."
    }
}

const getTitle = (mode) => {
    switch (mode) {
        case MODE.EDIT:
            return "상품 수정"
        case MODE.ADD:
            return "상품 등록"
        case MODE.DETAIL:
            return "상품 상세"
    }
}

const getButtonTitle = (mode) => {
    switch (mode) {
        case MODE.EDIT:
            return "수정"
        case MODE.ADD:
            return "등록"
        case MODE.DETAIL:
            return "닫기"
    }
}

export default GoodsModal