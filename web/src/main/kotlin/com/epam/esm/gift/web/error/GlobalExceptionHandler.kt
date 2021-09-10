package com.epam.esm.gift.web.error

import com.epam.esm.gift.TimeService
import com.epam.esm.gift.error.EntityDuplicateException
import com.epam.esm.gift.error.EntityNotFoundException
import mu.KLogging
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messages: MessageSource,
    private val timeService: TimeService,
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleDefault(ex: Exception, locale: Locale, timeZone: TimeZone): ApiError {
        logger.error(ex.message, ex)
        return ApiError(
            message = messages.getMessage(ERROR_UNCATEGORIZED, locale),
            timestamp = timeService.now(timeZone)
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(
        ex: EntityNotFoundException, locale: Locale, timeZone: TimeZone
    ): ApiError {
        logger.error(ex.message, ex)
        return ApiError(
            message = messages.getMessage(
                ERROR_REST_RESOURCE_NOT_FOUND,
                arrayOf(ex.name, ex.condition),
                locale
            ),
            timestamp = timeService.now(timeZone),
            code = ErrorCode.NOT_FOUND
        )
    }

    @ExceptionHandler(EntityDuplicateException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleEntityDuplicateException(
        ex: EntityDuplicateException, locale: Locale, timeZone: TimeZone
    ): ApiError {
        logger.error(ex.message, ex)
        return ApiError(
            message = messages.getMessage(
                ERROR_REST_RESOURCE_DUPLICATE,
                arrayOf(ex.name, ex.condition),
                locale
            ),
            timestamp = timeService.now(timeZone),
            code = ErrorCode.DUPLICATE
        )
    }

    private fun MessageSource.getMessage(code: String, locale: Locale): String {
        return getMessage(code, null, locale)
    }

    companion object : KLogging() {
        private const val ERROR_UNCATEGORIZED = "error.uncategorized"
        private const val ERROR_REST_RESOURCE_NOT_FOUND = "error.rest.resource.not.found"
        private const val ERROR_REST_RESOURCE_DUPLICATE = "error.rest.resource.duplicate"
    }
}