package com.musinsa.coordinator.exception

import com.musinsa.coordinator.constants.MusinsaError
import com.musinsa.coordinator.entity.Goods
import com.musinsa.coordinator.exception.MusinsaException.Companion.EXTENSION_CODE
import com.musinsa.coordinator.model.LowPriceBrandGoods
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.time.LocalDateTime

class MusinsaExceptionTest : BehaviorSpec({
    Given("Musinsa exception 생성") {
        `when`("NOT_EXIST_GOODS 익셉션을 생성하면") {
            val exception = MusinsaException(musinsaError, musinsaError.desc)
            Then("에러 코드는 Musinsa Error code 와 같아야 한다.") {
                exception.extensions[EXTENSION_CODE]?.shouldBeEqual(musinsaError.code)
            }
            Then("에러 메시지는 Musinsa Error message 와 같아야 한다.") {
                exception.getMessage() shouldBe musinsaError.desc
            }
            Then("에러 타입은 Musinsa Error 와 같아야 한다.") {
                exception.errorType shouldBe musinsaError
            }
            Then("에러 로케이션은 노출되지 않는 값이므로 null 이어야 한다.") {
                exception.locations shouldBe null
            }
        }
    }
}) {
    companion object {
        private val musinsaError = MusinsaError.NOT_EXIST_GOODS
    }
}