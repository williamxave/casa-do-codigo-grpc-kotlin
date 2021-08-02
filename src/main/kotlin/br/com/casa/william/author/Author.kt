package br.com.casa.william.author

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Author(
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val name: String,
    @field:NotBlank @field:Size(max = 400) val description: String,
    @field:NotNull val createdAt: LocalDateTime
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bankId: Long? = null

    val externalId = UUID.randomUUID()
}
