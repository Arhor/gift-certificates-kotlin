package com.epam.esm.gift.web.error

import java.time.LocalDateTime
import java.time.ZoneOffset

data class ApiError(
    val message: String,
    val code: ErrorCode = ErrorCode.UNCATEGORIZED,
    val timestamp: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC),
)
