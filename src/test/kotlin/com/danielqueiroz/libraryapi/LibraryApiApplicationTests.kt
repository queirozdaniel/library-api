package com.danielqueiroz.libraryapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class LibraryApiApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val book_api = "/api/books"

    @Test
    fun `create a new book`() {

        val json = ObjectMapper().writeValueAsString(null)

        val request = post(book_api)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("")

        mockMvc.perform(request)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").isEmpty)
            .andExpect(jsonPath("title").value("Meu livro"))
            .andExpect(jsonPath("author").value("Autor"))
            .andExpect(jsonPath("isbn").value("121212"))
    }


}
