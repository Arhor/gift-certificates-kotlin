package com.epam.esm.gift.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tags")
open class Tag : AbstractIdentifiable<Long>() {

    @Column(name = "name")
    open var name: String? = null
}