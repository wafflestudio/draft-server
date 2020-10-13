package com.wafflestudio.draft

import org.springframework.boot.SpringApplication

object DraftApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(DraftApplication::class.java, *args)
    }
}