package com.danielqueiroz.libraryapi.api.controller

import com.danielqueiroz.libraryapi.api.dto.form.NewLoanForm
import com.danielqueiroz.libraryapi.domain.model.Loan
import com.danielqueiroz.libraryapi.domain.service.BookService
import com.danielqueiroz.libraryapi.domain.service.LoanService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/api/loans")
class LoanController(
    private val loanService: LoanService,
    private val bookService: BookService
) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid form: NewLoanForm): Long? {
        val book = bookService.getBookByIsbn(form.isbn).orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn") }
        var loan = Loan(book = book, costumer = form.costumer, loanDate = LocalDate.now(), returned = false)

        loan = loanService.save(loan)

        return loan.id
    }


}