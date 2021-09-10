package com.epam.esm.gift.model

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractIdentifiable<T> : Identifiable<T>
    where T : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: T? = null
}