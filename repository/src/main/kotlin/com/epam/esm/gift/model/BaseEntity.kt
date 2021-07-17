package com.epam.esm.gift.model

import java.io.Serializable

interface BaseEntity<T : Serializable> {

    var id: T?
}