package com.danielqueiroz.libraryapi.domain.model

import javax.persistence.*

@Entity
@Table
data class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,
    var author: String,
    var isbn: String
)