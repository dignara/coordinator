package com.musinsa.coordinator

import com.musinsa.coordinator.annotation.ExcludeFromJacocoGeneratedReport
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoordinatorApplication
@ExcludeFromJacocoGeneratedReport
fun main(args: Array<String>) {
	runApplication<CoordinatorApplication>(*args)
}
