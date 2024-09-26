package com.musinsa.coordinator.model

import com.musinsa.coordinator.entity.Goods
import java.math.BigDecimal

class LowPriceGoodsByCategory(goods: Iterable<Goods>) {
    var totalPrice: BigDecimal = BigDecimal.ZERO
        private set

    var goods: Iterable<Goods> = emptyList()
        private set
    init {
        goods
            .distinctBy { it.category }.also { this.goods = it }
            .sumOf { it.price }.also { this.totalPrice = it }
    }
}