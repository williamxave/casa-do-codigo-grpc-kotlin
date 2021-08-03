package br.com.casa.william.category

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByName(name: String): Boolean
}