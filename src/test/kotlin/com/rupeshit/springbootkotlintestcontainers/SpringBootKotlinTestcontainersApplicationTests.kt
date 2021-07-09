package com.rupeshit.springbootkotlintestcontainers

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class SpringBootKotlinTestcontainersApplicationTests {

	companion object {
		@Container
		private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest")

		@DynamicPropertySource
		@JvmStatic
		fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
			registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
			registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
		}
	}

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Test
	fun contextLoads() {
	}

	@Test
	fun `should be connect to Postgres version 13`() {
		val actualDatabaseVersion = jdbcTemplate.queryForObject("SELECT version()", String::class.java)
		actualDatabaseVersion shouldContain "PostgreSQL 13.3"
	}

}
