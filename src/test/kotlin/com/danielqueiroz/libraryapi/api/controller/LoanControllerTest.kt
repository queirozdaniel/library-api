package com.danielqueiroz.libraryapi.api.controller

import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.model.Loan
import com.danielqueiroz.libraryapi.domain.service.BookService
import com.danielqueiroz.libraryapi.domain.service.LoanService
import com.danielqueiroz.libraryapi.helper.anyObject
import com.danielqueiroz.libraryapi.helper.createNewLoanForm
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.util.*

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@WebMvcTest(controllers = [LoanController::class])
@AutoConfigureMockMvc
class LoanControllerTest {

    private final val LOAN_API = "/api/loans"

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @MockBean
    private lateinit var loanService: LoanService

    @Test
    fun `create one loan`(){

        val dto = createNewLoanForm()
        val returnedBook = Book(1L, title = "Novo Mundo", author = "Daniel", isbn = dto.isbn)
        val loan = Loan(1, "Fulano", book = returnedBook, loanDate = LocalDate.now(), returned = false)

        BDDMockito.given( bookService.getBookByIsbn(dto.isbn))
            .willReturn(Optional.of(returnedBook))

        BDDMockito.given(loanService.save(anyObject())).willReturn(loan)

        val json = ObjectMapper().writeValueAsString(dto)

        val request = MockMvcRequestBuilders.post(LOAN_API)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().string("1"))
    }

    @Test
    fun `returns error when trying to borrow from non-existent book`(){

        val dto = createNewLoanForm()

        BDDMockito.given( bookService.getBookByIsbn(dto.isbn))
            .willReturn(Optional.empty())

        val json = ObjectMapper().writeValueAsString(dto)

        val request = MockMvcRequestBuilders.post(LOAN_API)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize<Int>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value("Book not found for passed isbn"))
    }

}