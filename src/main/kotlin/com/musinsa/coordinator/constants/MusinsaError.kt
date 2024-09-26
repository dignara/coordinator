package com.musinsa.coordinator.constants

import graphql.ErrorClassification

enum class MusinsaError(val code: Int, val desc: String): ErrorClassification {
    NOT_EXIST_GOODS(1001, "존재하지 않는 상품입니다.");
}