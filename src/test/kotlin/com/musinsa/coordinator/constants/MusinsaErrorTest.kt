package com.musinsa.coordinator.constants

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MusinsaErrorTest : FunSpec({
    test("에러값 확인") {
        val musinsaError = MusinsaError.NOT_EXIST_GOODS
        musinsaError.code shouldBe 1001
        musinsaError.desc shouldBe "존재하지 않는 상품입니다."
    }
})
