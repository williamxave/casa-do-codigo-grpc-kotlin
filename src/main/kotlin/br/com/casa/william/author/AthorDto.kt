package br.com.casa.william.author

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class AthorDto(
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val name: String,
    @field:NotBlank @field:Size(max = 400) val description: String,
    @field:NotNull val createdAt: LocalDateTime
)
