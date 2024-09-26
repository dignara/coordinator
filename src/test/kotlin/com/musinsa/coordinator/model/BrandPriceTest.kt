package com.musinsa.coordinator.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigDecimal

class BrandPriceTest : BehaviorSpec({
    Given("객체 생성 테스트") {
        val brand = "brand"
        val price = BigDecimal(1000)
        `when`("'brand'의 1000원 객체를 생성하면") {
            val brandPrice = BrandPrice(brand, price)
            Then("brand는 입력값과 같아야 한다.") {
                brandPrice.brand shouldBeEqual brand
            }
            Then("price는 입력값과 같아야 한다.") {
                brandPrice.price shouldBeEqual price
            }
        }
    }
})