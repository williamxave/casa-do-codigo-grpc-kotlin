package br.com.casa.william.validator

import br.com.casa.william.category.CategoryDto
import br.com.casa.william.category.CategoryRepository
import br.com.casa.william.customexceptions.NameValidatorException
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
@Constraint(validatedBy = [ExistCategoryValidator::class])
annotation class ExistCategory(
    val message: String = "Category already registered",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ExistCategoryValidator(
    val categoryRepository: CategoryRepository
) : ConstraintValidator<ExistCategory, CategoryDto> {
    override fun isValid(
        value: CategoryDto,
        annotationMetadata: AnnotationValue<ExistCategory>,
        context: ConstraintValidatorContext
    ): Boolean {
        val possibleName = categoryRepository.existsByName(value.name)
        if (possibleName) {
            throw NameValidatorException("Category already registered")
        }
        return true
    }
}