package com.epam.esm.gift.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CertificateDTO(
    val id: Long?,
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val duration: Int?,
    val dateTimeCreated: LocalDateTime?,
    val dateTimeUpdated: LocalDateTime?,
    var tags: List<TagDTO>?,
)
