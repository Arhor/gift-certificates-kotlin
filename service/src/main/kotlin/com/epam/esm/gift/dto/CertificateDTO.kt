package com.epam.esm.gift.dto

import java.math.BigDecimal
import java.time.Instant

data class CertificateDTO(
    val id: Long?,
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val duration: Int?,
    val dateTimeCreated: Instant?,
    val dateTimeUpdated: Instant?,
    var tags: List<TagDTO>?,
)
