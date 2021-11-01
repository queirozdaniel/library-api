package com.danielqueiroz.libraryapi.api.dto.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class NewBookForm(

    @field:NotEmpty
    val title: String,

    @field:NotEmpty
    val author: String,

    @field:NotEmpty
    val isbn: String
) {
    constructor() : this("", "", "")
}
