package com.danielqueiroz.libraryapi.core.mapper

/**
 *  @property I represents Input class
 *  @property O represents Output class
 */
interface Mapper<I, O> {
    fun map(input: I) : O
}