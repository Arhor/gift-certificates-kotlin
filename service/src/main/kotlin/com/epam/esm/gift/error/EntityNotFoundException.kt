package com.epam.esm.gift.error

data class EntityNotFoundException(
    val name: String,
    val condition: String,
) : ServiceLayerException()