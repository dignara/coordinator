package com.musinsa.coordinator.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class ViewController {
    companion object {
        const val PAGE_MAIN = "main"
    }

    @GetMapping(value = ["/"])
    fun home(): String {
        return PAGE_MAIN
    }
}