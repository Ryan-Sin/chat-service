package com.ryan.sionic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SionicApplication

fun main(args: Array<String>) {
    runApplication<SionicApplication>(*args)
}
