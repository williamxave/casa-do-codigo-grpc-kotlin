package br.com.casa.william.category

import br.com.casa.william.validator.ExistCategory
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
@ExistCategory
data class CategoryDto(
    @field:NotBlank val name: String
)
