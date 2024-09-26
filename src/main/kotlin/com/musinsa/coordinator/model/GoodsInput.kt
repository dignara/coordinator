package com.musinsa.coordinator.model

import java.math.BigDecimal

data class GoodsInput(
    val id: Long,
    val brand: String,
    val category: String,
    val price: BigDecimal
)