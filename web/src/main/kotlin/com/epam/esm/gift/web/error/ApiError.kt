package com.epam.esm.gift.web.error

import java.time.LocalDateTime

data class ApiError(
    val message: String,
    val timestamp: LocalDateTime,
    val code: ErrorCode = ErrorCode.UNCATEGORIZED,
)
