package com.musinsa.coordinator.model

import com.musinsa.coordinator.entity.Goods
import java.math.BigDecimal

data class LowPriceBrandGoods(
    val brand: String,
    val goods: Iterable<Goods>
) {
    fun getTotalPrice(): BigDecimal {
        return goods.sumOf { it.price }
    }
}