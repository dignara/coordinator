import {gql} from "@apollo/client";

export const FRAGMENT_GOODS = gql`
    fragment goods on Goods {
        id
        brand
        category
        price 
    }
`

export const QUERY_GET_GOODS = gql`
    query GetLowPriceGoodsByCategory {
        lowPriceGoodsByCategory {
            totalPrice
            goods {
                ...goods   
            }
        }   
    }
    ${FRAGMENT_GOODS}
`

export const QUERY_GET_LOW_PRICE_BRAND_GOODS = gql`
    query GetLowPriceBrandGoods {
        lowPriceBrandGoods {
            totalPrice
            brand
            goods {
                ...goods
            }
        }
    }
    ${FRAGMENT_GOODS}
`

export const QUERY_GET_HIGH_AND_LOW_PRICE_GOODS = gql`
    query GetCategoryHighAndLowPriceGoods($category: String!) {
        categoryHighAndLowPriceGoods(category: $category) {
            category
            highPriceGoods {
                ...goods
            }
            lowPriceGoods {
                ...goods
            }
        }
    }
    ${FRAGMENT_GOODS}
`
export const MUTATION_GOODS_SAVE = gql`
    mutation SaveGoods($goodsInput: GoodsInput!) {
        saveGoods(goodsInput: $goodsInput) {
            ...goods
        }
    }
    ${FRAGMENT_GOODS}
`

export const MUTATION_GOODS_DELETE = gql`
    mutation DeleteGoods($id: ID!) {
        deleteGoods(id: $id) {
            ...goods
        }
    }
    ${FRAGMENT_GOODS}
`


