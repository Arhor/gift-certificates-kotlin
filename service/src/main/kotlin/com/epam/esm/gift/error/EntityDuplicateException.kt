package com.epam.esm.gift.error

data class EntityDuplicateException(
    val name: String,
    val condition: String,
) : ServiceLayerException()