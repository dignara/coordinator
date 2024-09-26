package com.musinsa.coordinator.controller

import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.model.CategoryHighAndLowPriceGoods
import com.musinsa.coordinator.model.GoodsInput
import com.musinsa.coordinator.model.LowPriceGoodsByCategory
import com.musinsa.coordinator.model.LowPriceBrandGoods
import com.musinsa.coordinator.service.GoodsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GoodsController(
    @Autowired
    private val goodsService: GoodsService
) {
    @QueryMapping
    fun lowPriceGoodsByCategory(): LowPriceGoodsByCategory {
        return goodsService.getLowPriceGoodsByCategory()
    }

    @QueryMapping
    fun lowPriceBrandGoods(): LowPriceBrandGoods {
        return goodsService.getLowPriceBrandGoods()
    }

    @QueryMapping
    fun categoryHighAndLowPriceGoods(@Argument category: String): CategoryHighAndLowPriceGoods {
        return goodsService.getCategoryHighAndLowPriceGoods(category)
    }

    @MutationMapping
    fun saveGoods(@Argument goodsInput: GoodsInput): Goods {
        return goodsService.saveGoods(goodsInput)
    }

    @MutationMapping
    fun deleteGoods(@Argument id: Long): Goods {
        return goodsService.delete(id)
    }
}