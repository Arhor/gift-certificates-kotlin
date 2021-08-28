package com.epam.esm.gift.model

import java.io.Serializable

interface Entity<T : Serializable> {

    var id: T?
}