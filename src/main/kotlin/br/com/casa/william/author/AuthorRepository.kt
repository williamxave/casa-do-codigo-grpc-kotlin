package br.com.casa.william.author

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface AuthorRepository : JpaRepository<Author, Long>{}