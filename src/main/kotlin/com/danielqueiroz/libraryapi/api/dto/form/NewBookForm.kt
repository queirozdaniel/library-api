package com.danielqueiroz.libraryapi.api.dto.form

import javax.validation.constraints.NotEmpty

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
