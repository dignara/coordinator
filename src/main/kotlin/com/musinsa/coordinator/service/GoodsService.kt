package com.musinsa.coordinator.service

import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.model.CategoryHighAndLowPriceGoods
import com.musinsa.coordinator.model.GoodsInput
import com.musinsa.coordinator.model.LowPriceBrandGoods
import com.musinsa.coordinator.model.LowPriceGoodsByCategory
import org.springframework.stereotype.Service

@Service
interface GoodsService {
    fun getLowPriceGoodsByCategory(): LowPriceGoodsByCategory

    fun getLowPriceBrandGoods(): LowPriceBrandGoods

    fun getCategoryHighAndLowPriceGoods(category: String): CategoryHighAndLowPriceGoods

    fun saveGoods(goodsInput: GoodsInput): Goods

    fun delete(id: Long): Goods
}