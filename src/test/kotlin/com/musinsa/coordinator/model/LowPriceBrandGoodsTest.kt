package com.musinsa.coordinator.model

import com.musinsa.coordinator.entity.Goods
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.time.LocalDateTime

class LowPriceBrandGoodsTest : BehaviorSpec({
    Given("객체 생성 테스트") {
        `when`("A brand의 상품 목록으로 객체를 생성하면") {
            val brandPrice = LowPriceBrandGoods(BRAND, brandGoods)
            Then("brand는 입력값과 같아야 한다.") {
                brandPrice.brand shouldBeEqual BRAND
            }
            Then("상품의 개수는 8개여야 한다") {
                brandPrice.goods.count() shouldBe 8
            }
        }
    }
}) {
    companion object {
        private val now = LocalDateTime.now()
        const val BRAND = "A"
        val brandGoods = listOf(
            Goods(1, "상의", BRAND, BigDecimal(10), now, now),
            Goods(2, "아우터", BRAND, BigDecimal(10), now, now),
            Goods(3, "바지", BRAND, BigDecimal(10), now, now),
            Goods(4, "스니커즈", BRAND, BigDecimal(10), now, now),
            Goods(5, "가방", BRAND, BigDecimal(10), now, now),
            Goods(6, "모자", BRAND, BigDecimal(10), now, now),
            Goods(7, "양말", BRAND, BigDecimal(10), now, now),
            Goods(8, "액세서리", BRAND, BigDecimal(10), now, now)
        )
    }
}
