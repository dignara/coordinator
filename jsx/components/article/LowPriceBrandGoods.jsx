import React from "react"
import {useQuery} from '@apollo/client';
import {QUERY_GET_LOW_PRICE_BRAND_GOODS} from "../query/goods";
import {errorLayout, loadingLayout} from "../layout/Layout";
import {showGoodsModal} from "../../modules/modalSlice";
import {useDispatch} from "react-redux";

const LowPriceBrandGoods = () => {
    const {loading, error, data} = useQuery(QUERY_GET_LOW_PRICE_BRAND_GOODS);
    const result = data?.lowPriceBrandGoods
    return (
        <section>
            <header className="d-flex">
                <h3>최저가격 {result ? `'${result.brand}'` : false} 브랜드 상품</h3>
                <h5 className="ms-auto me-3">{result ? result.totalPrice.toLocaleString() : false}</h5>
            </header>
            <div>
                <RenderBody error={error} loading={loading} result={result} />
            </div>
        </section>
    )
}

const RenderBody = ({loading, error, result}) => {
    const dispatch = useDispatch()
    if (loading) return loadingLayout("로딩중...", "잠시만 기다려주세요.")
    if (error) return errorLayout("로딩실패", error.message)
    return (
        <ul className="list-group">
            {
                result?.goods.map(item => (
                    <a className="list-group-item list-group-item-action d-flex justify-content-between align-items-center" onClick={() => dispatch(showGoodsModal({goods: item}))}>
                        <div className="brand">
                            <i className="bi bi-bag-fill fs-3"/>
                            <span className="ms-3">{item.category}</span>
                        </div>
                        <span>{item.price.toLocaleString()}</span>
                    </a>
                ))
            }
        </ul>
    )
}

export default LowPriceBrandGoods