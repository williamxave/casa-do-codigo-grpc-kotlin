package br.com.casa.william.author

import io.micronaut.validation.Validated
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class AuthorValidate(
    val authorRepository: AuthorRepository
) {
    fun validate(@Valid authorDto: AuthorDto): Author {
        authorDto.run {
            Author(
                email = authorDto.email,
                name = authorDto.name,
                description = authorDto.description,
                createdAt = authorDto.createdAt
            )
        }.run {
            authorRepository.save(this)
            return this
        }
    }
}
//        val author = Author(
//            email = authorDto.email,
//            name = authorDto.name,
//            description = authorDto.description,
//            createdAt = authorDto.createdAt
//        )
//        authorRepository.save(author)
//        return author
//
//    }
