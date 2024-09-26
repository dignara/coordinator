import React, {useContext} from "react"
import {useLazyQuery} from '@apollo/client';
import {QUERY_GET_HIGH_AND_LOW_PRICE_GOODS} from "../query/goods";
import {errorLayout, loadingLayout} from "../layout/Layout";
import Select from 'react-select';
import {CATEGORIES} from "../../Constants";
import {ArticleContext} from "../provider/ArticleContext";
import {showGoodsModal} from "../../modules/modalSlice";
import {useDispatch} from "react-redux";

const CategoryHighAndLowPriceGoods = () => {
    const [{category}, actions] = useContext(ArticleContext)
    const [loadHighAndLowPriceGoods, {called, loading, error, data}] = useLazyQuery(QUERY_GET_HIGH_AND_LOW_PRICE_GOODS);
    const result = data?.categoryHighAndLowPriceGoods

    React.useEffect(() => {
        category && requestHighAndLowPriceGoods(category)
    }, [category])

    const requestHighAndLowPriceGoods = (category) => {
        loadHighAndLowPriceGoods({variables: {category: category}})
            .then(result => {
                actions.setCategory(result?.data?.categoryHighAndLowPriceGoods?.category)
            }
        )
    }

    return (
        <>
            <section>
                <Select
                    value={category ? {value: category, label: category} : undefined}
                    isDisabled={called && loading}
                    placeholder="카테고리를 선택하세요."
                    onChange={category => requestHighAndLowPriceGoods(category.value)}
                    options={CATEGORIES.map(category => ({value: category, label: category}))}
                />
            </section>
            {
                called && loading ? loadingLayout("로딩중...", "잠시만 기다려주세요.") : error ? errorLayout("로딩실패", error.message) : result &&
                    <>
                        <RenderBody title={'최저가'} category={result.category} goods={result.lowPriceGoods}/>
                        <RenderBody title={'최고가'} category={result.category} goods={result.highPriceGoods}/>
                    </>
            }
        </>

    )
}

const RenderBody = ({title, category, goods}) => {
    const dispatch = useDispatch()
    return (
        <section>
            <header>
                <h3>{title} {category ? `'${category}'` : false} 상품</h3>
            </header>
            <div>
                <ul className="list-group">
                    {
                        goods.map(item => (
                            <a className="list-group-item list-group-item-action d-flex justify-content-between align-items-center" onClick={() => dispatch(showGoodsModal({goods: item}))}>
                                <div className="brand">
                                    <i className="bi bi-bag-fill fs-3"/>
                                    <span className="brand-name">{item.brand}</span>
                                </div>
                                <span>{item.price.toLocaleString()}</span>
                            </a>
                        ))
                    }
                </ul>
            </div>
        </section>
    )
}

export default CategoryHighAndLowPriceGoods