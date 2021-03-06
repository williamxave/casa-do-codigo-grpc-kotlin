package br.com.casa.william.author

import br.com.casa.william.AuthorRequest
import java.time.LocalDateTime

 fun AuthorRequest.toModel(): AuthorDto {
    return AuthorDto(
        email = this.email,
        name = this.name,
        description = this.description,
        createdAt = LocalDateTime.now()
    )
}