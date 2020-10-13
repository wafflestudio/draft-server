package com.wafflestudio.draft

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DraftApplication

fun main(args: Array<String>) {
    runApplication<DraftApplication>(*args)
}
