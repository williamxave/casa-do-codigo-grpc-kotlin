package br.com.casa.william.validator

import br.com.casa.william.author.AuthorDto
import br.com.casa.william.author.AuthorRepository
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistEmailValidator::class])
annotation class ExistEmail(
    val message: String = "Email already registered",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ExistEmailValidator(
    val authorRepository: AuthorRepository
) : ConstraintValidator<ExistEmail, AuthorDto> {
    override fun isValid(
        value: AuthorDto,
        annotationMetadata: AnnotationValue<ExistEmail>,
        context: ConstraintValidatorContext
    ): Boolean {
        val possibleEmail = authorRepository.existsByEmail(value.email)
        if (possibleEmail) {
            return false
        }
        return true
    }
}