package com.danielqueiroz.libraryapi.domain.model

import java.time.LocalDate

data class Loan(
    val id: Long? = null,
    val costumer: String,
    val book: Book,
    val loanDate: LocalDate = LocalDate.now(),
    val returned: Boolean
)
