package com.rupeshit.springbootkotlintestcontainers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootKotlinTestcontainersApplication

fun main(args: Array<String>) {
	runApplication<SpringBootKotlinTestcontainersApplication>(*args)
}
