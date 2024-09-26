package com.musinsa.coordinator.controller

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.hamcrest.CoreMatchers.containsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ViewControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
) : DescribeSpec({
    describe("웹 접속 테스트") {
        it("웹 루트 접속시 응답해야 한다.") {
            val result = mockMvc.perform(
                get("/")
                    .contentType(MediaType.TEXT_HTML))
                .andReturn()
            result.response.status shouldBe HttpStatus.OK.value()
        }
    }
})
