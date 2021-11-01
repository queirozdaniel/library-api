package com.danielqueiroz.libraryapi.domain.model

import javax.persistence.*

@Entity
@Table
data class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val title: String,
    val author: String,
    val isbn: String
)