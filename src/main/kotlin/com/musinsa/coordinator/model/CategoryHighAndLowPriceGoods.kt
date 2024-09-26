package com.musinsa.coordinator.model

import com.musinsa.coordinator.entity.Goods

data class CategoryHighAndLowPriceGoods(
    val category: String,
    val highPriceGoods: Iterable<Goods>,
    val lowPriceGoods: Iterable<Goods>
)