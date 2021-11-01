package com.danielqueiroz.libraryapi.api.exception

import com.danielqueiroz.libraryapi.api.dto.exception.ApiError
import com.danielqueiroz.libraryapi.domain.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandlerAPI {


    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidation(exception: MethodArgumentNotValidException, request: HttpServletRequest) : ApiError {
        val listError = exception.bindingResult.fieldErrors.map { e -> e.defaultMessage }
        return ApiError(listError)
    }


    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBusinessError(exception: BusinessException, request: HttpServletRequest) : ApiError {
        return ApiError(listOf(exception.message))
    }

}