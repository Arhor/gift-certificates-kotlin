package com.epam.esm.gift.web.error

import com.epam.esm.gift.error.EntityDuplicateException
import mu.KLogging
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler(private val messages: MessageSource) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleDefault(ex: Exception, locale: Locale): ApiError {
        logger.error(ex.message, ex)
        return ApiError(messages.getMessage(ERROR_UNCATEGORIZED, locale))
    }


    @ExceptionHandler(EntityDuplicateException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleEntityDuplicateException(ex: EntityDuplicateException, locale: Locale): ApiError {
        logger.error(ex.message, ex)
        return ApiError(
            message = messages.getMessage(
                ERROR_REST_RESOURCE_DUPLICATE,
                arrayOf(ex.name, ex.condition),
                locale
            ),
            code = ErrorCode.DUPLICATE
        )
    }

    private fun MessageSource.getMessage(code: String, locale: Locale): String {
        return getMessage(code, null, locale)
    }

    companion object : KLogging() {
        private const val ERROR_UNCATEGORIZED = "error.uncategorized"
        private const val ERROR_REST_RESOURCE_DUPLICATE = "error.rest.resource.duplicate"
    }
}