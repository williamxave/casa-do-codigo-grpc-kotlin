package br.com.casa.william.category

import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
class CategoryValidate(
    val categoryRepository: CategoryRepository
) {

    fun validate(@Valid categoryDto: CategoryDto) : Category{
        categoryDto.run {
            Category(
                name = categoryDto.name
            )
        }.run {
            categoryRepository.save(this)
            return this
        }
    }
}