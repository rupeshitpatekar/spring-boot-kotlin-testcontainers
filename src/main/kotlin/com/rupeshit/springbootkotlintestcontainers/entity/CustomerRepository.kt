package com.rupeshit.springbootkotlintestcontainers.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
    fun findByName(name: String): List<Customer>
}