package com.danielqueiroz.libraryapi.api.dto.form

import javax.validation.constraints.NotEmpty

data class NewLoanForm(

    @field:NotEmpty
    val isbn: String,

    @field:NotEmpty
    val costumer: String
)
