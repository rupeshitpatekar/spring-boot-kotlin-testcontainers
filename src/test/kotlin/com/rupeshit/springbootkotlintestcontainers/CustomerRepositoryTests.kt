package com.rupeshit.springbootkotlintestcontainers

import com.rupeshit.springbootkotlintestcontainers.entity.Customer
import com.rupeshit.springbootkotlintestcontainers.entity.CustomerRepository
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.longs.shouldBeInRange
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CustomerRepositoryTests {

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
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun `when record is saved then the id is populated`() {
        val tom = customerRepository.save(Customer(name = "Tom Smith", password = "Tom"))
        tom.id shouldBeInRange (1..Long.MAX_VALUE)
    }

    @Test
    fun `when multiple records with the same name then all are found`() {
        customerRepository.save(Customer(name = "Tom Smith", password= "Tom"))
        customerRepository.save(Customer(name = "Mark Smith", password = "Tom"))
        customerRepository.save(Customer(name = "Thomas Doe", password = "Tom"))
        customerRepository.save(Customer(name = "Mark Smith", password = "Test"))

        val actual = customerRepository.findByName("Mark Smith")

        actual shouldHaveSize 2
    }

}