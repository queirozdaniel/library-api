package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.model.Loan
import org.springframework.stereotype.Service

@Service
interface LoanService {

    fun save(loan: Loan): Loan

}