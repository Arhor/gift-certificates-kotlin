package com.epam.esm.gift.model

import java.io.Serializable

interface Identifiable<T : Serializable> {

    var id: T?
}