package com.epam.esm.gift.web.error

import com.epam.esm.gift.web.error.ErrorCode.Type.*
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = ErrorCodeSerializer::class)
enum class ErrorCode(val type: Type, val code: Int) {
    // @formatter:off

    UNCATEGORIZED      (type = GEN, code = 0),

    VALIDATION_FAILED  (type = VAL, code = 0),

    SECURITY_VIOLATION (type = SEC, code = 0),

    DATA_ACCESS_ERROR  (type = DAT, code = 0),
    NOT_FOUND          (type = DAT, code = 1),
    DUPLICATE          (type = DAT, code = 2),

    // @formatter:on
    ;

    enum class Type(val description: String) {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        ;
    }
}