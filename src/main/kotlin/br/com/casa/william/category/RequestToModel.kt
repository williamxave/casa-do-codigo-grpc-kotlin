package br.com.casa.william.category

import br.com.casa.william.CategoryRequest

fun CategoryRequest.toModel() : CategoryDto{
    return CategoryDto(
        name = this.name
    )
}