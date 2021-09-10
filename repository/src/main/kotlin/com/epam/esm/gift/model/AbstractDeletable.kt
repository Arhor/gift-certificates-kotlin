package com.epam.esm.gift.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractDeletable<T> : AbstractAuditable<T>(), Deletable
    where T : Serializable {

    @Column(name = "deleted")
    override var deleted: Boolean = false
}