package com.musinsa.coordinator.entity

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.math.BigDecimal
import java.time.LocalDateTime

class GoodsTest : BehaviorSpec({
    Given("시간 갱신 테스트") {
        `when`("현재 시간으로 업데이트하면") {
            goods.createdAt = update
            goods.updatedAt = update

            Then("과거 시간과 같지 않아야 한다.") {
                goods.createdAt!! shouldNotBeEqual now
                goods.updatedAt!! shouldNotBeEqual now
            }
            Then("현재 시간과 같아야 한다.") {
                goods.createdAt!! shouldBeEqual update
                goods.updatedAt!! shouldBeEqual update
            }
        }
    }
}) {
    companion object {
        private val now = LocalDateTime.now()
        private val update = LocalDateTime.now()
        private val goods = Goods(
            0L,
            "category",
            brand = "brand",
            price = BigDecimal(1000),
            now,
            now
        )
    }
}