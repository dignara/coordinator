package com.musinsa.coordinator.service

import com.musinsa.coordinator.constants.MusinsaError
import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.exception.MusinsaException
import com.musinsa.coordinator.model.CategoryHighAndLowPriceGoods
import com.musinsa.coordinator.model.GoodsInput
import com.musinsa.coordinator.model.LowPriceBrandGoods
import com.musinsa.coordinator.model.LowPriceGoodsByCategory
import com.musinsa.coordinator.repository.GoodsRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GoodsServiceImpl(
    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val goodsRepository: GoodsRepository
) : GoodsService {
    @Transactional(readOnly = true)
    override fun getLowPriceGoodsByCategory(): LowPriceGoodsByCategory {
        return LowPriceGoodsByCategory(goodsRepository.findAllByLowPriceByCategory())
    }

    @Transactional(readOnly = true)
    override fun getLowPriceBrandGoods(): LowPriceBrandGoods {
        val brandPrices = goodsRepository.findAllByBrandTotalPrice(Sort.by(ALIAS_BRAND_PRICE))
        return brandPrices.first().brand.let { LowPriceBrandGoods(it, goodsRepository.findAllByBrand(it)) }
    }

    @Transactional
    override fun saveGoods(goodsInput: GoodsInput): Goods {
        val goods: Goods
        when (goodsInput.id) {
            0L -> goods = modelMapper.map(goodsInput, Goods::class.java)
            else -> {
                goods = getGoods(goodsInput.id)
                modelMapper.map(goodsInput, goods)
            }
        }
        return goodsRepository.save(goods)
    }

    @Transactional(readOnly = true)
    override fun getCategoryHighAndLowPriceGoods(category: String): CategoryHighAndLowPriceGoods {
        val highPriceGoods = goodsRepository.findAllByCategoryHighPrice(category)
        val lowPriceGoods = goodsRepository.findAllByCategoryLowPrice(category)
        return CategoryHighAndLowPriceGoods(category, highPriceGoods, lowPriceGoods)
    }

    @Transactional
    override fun delete(id: Long): Goods {
        val goods = getGoods(id)
        goodsRepository.deleteById(id)
        return goods
    }

    private fun getGoods(id: Long): Goods {
        return goodsRepository.findById(id).orElseThrow { MusinsaException(MusinsaError.NOT_EXIST_GOODS) }
    }

    companion object {
        const val ALIAS_BRAND_PRICE = "brandPrice"
    }
}