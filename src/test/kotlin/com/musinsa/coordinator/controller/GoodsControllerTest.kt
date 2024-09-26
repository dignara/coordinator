package com.musinsa.coordinator.controller

import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.model.CategoryHighAndLowPriceGoods
import com.musinsa.coordinator.model.GoodsInput
import com.musinsa.coordinator.model.LowPriceBrandGoods
import com.musinsa.coordinator.model.LowPriceBrandGoodsTest.Companion.BRAND
import com.musinsa.coordinator.model.LowPriceBrandGoodsTest.Companion.brandGoods
import com.musinsa.coordinator.model.LowPriceGoodsByCategory
import com.musinsa.coordinator.service.GoodsService
import com.musinsa.coordinator.service.GoodsServiceTest.Companion.lowPriceByCategoryGoods
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.graphql.test.tester.GraphQlTester
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
class GoodsControllerTest(
    @Autowired
    private val graphQlTester: GraphQlTester,

    @MockBean
    private val goodsService: GoodsService
) : FunSpec({
    isolationMode = IsolationMode.InstancePerTest

    test("카테고리별 최저가격 브랜드와 상품 리스트") {
        val document = """
            query {
                lowPriceGoodsByCategory {
                    totalPrice
                    goods {
                        brand
                        category
                        price
                    }
                }
            }
        """
        `when`(goodsService.getLowPriceGoodsByCategory()).thenReturn(lowPriceGoodsByCategory)
        graphQlTester.document(document)
            .execute()
            .path("lowPriceGoodsByCategory")
            .entity(LowPriceGoodsByCategory::class.java)
            .satisfies {
                run {
                    it.totalPrice shouldBeEqual BigDecimal(8000)
                    it.goods.count() shouldBeEqual 8
                }
            }
    }

    test("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격 브랜드의 상품") {
        val document = """
            query {
                lowPriceBrandGoods {
                    totalPrice
                    brand
                    goods {
                        id
                        brand
                        category
                        price
                    }
                }
            }
        """
        `when`(goodsService.getLowPriceBrandGoods()).thenReturn(lowPriceBrandGoods)
        graphQlTester.document(document)
            .execute()
            .path("lowPriceBrandGoods")
            .entity(LowPriceBrandGoods::class.java)
            .satisfies {
                run {
                    it.brand shouldBe BRAND
                    it.getTotalPrice() shouldBeEqual BigDecimal(80)
                    it.goods.count() shouldBeEqual 8
                }
            }
    }

    xtest("카테고리 이름으로 최저, 최고가격 상품") {
        val document = """
            query {
                categoryHighAndLowPriceGoods(category: "$CATEGORY") {
                    category
                    highPriceGoods {
                        id
                        brand
                        price
                    }
                    lowPriceGoods {
                        id
                        brand
                        price
                    }
                }
            }
        """
        `when`(goodsService.getCategoryHighAndLowPriceGoods(CATEGORY)).thenReturn(categoryHighAndLowPriceGoods)
        graphQlTester.document(document)
            .execute()
            .path("categoryHighAndLowPriceGoods")
            .entity(CategoryHighAndLowPriceGoods::class.java)
            .satisfies {
                run {
                    it.category shouldBeEqual CATEGORY
                    it.highPriceGoods.count() shouldBeEqual 1
                    it.lowPriceGoods.count() shouldBeEqual 1
                }
            }
    }

    xtest("상품 등록/수정") {
        val goodsInput = GoodsInput(0L, "brand", "category", BigDecimal(100))
        val document = """
            mutation {
                saveGoods(GoodsInput: "$goodsInput") {
                    id
                    brand
                    category
                    price
                }
            }
        """
        `when`(goodsService.saveGoods(goodsInput)).thenReturn(goods)
        graphQlTester.document(document)
            .execute()
            .path("saveGoods")
            .entity(Goods::class.java)
            .satisfies {
                run {
                    it.category shouldBeEqual "brand"
                }
            }
    }

    test("상품 삭제") {
        val id = 1L
        val document = """
            mutation {
                deleteGoods(id: "$id") {
                    id
                    brand
                    category
                    price
                }
            }
        """
        `when`(goodsService.delete(id)).thenReturn(goods)
        graphQlTester.document(document)
            .execute()
            .path("deleteGoods")
            .entity(Goods::class.java)
            .satisfies {
                run {
                    it.id shouldBeEqual id
                }
            }
    }
}) {
    companion object {
        const val CATEGORY = "상의"
        private val now = LocalDateTime.now()
        private val goods = Goods(1, CATEGORY, "A", BigDecimal(10), now, now)
        private val goodsList = listOf(goods)
        private val lowPriceGoodsByCategory = LowPriceGoodsByCategory(lowPriceByCategoryGoods)
        private val lowPriceBrandGoods = LowPriceBrandGoods(BRAND, brandGoods)
        private val categoryHighAndLowPriceGoods = CategoryHighAndLowPriceGoods(CATEGORY, goodsList, goodsList)
    }
}
