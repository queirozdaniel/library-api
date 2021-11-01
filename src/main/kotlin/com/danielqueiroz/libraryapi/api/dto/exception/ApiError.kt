package com.danielqueiroz.libraryapi.api.dto.exception

data class ApiError(
    val errors: List<String?>
)