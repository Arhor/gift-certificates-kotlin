package com.epam.esm.gift.model

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable

@Embeddable
open class CompositeId<A, B>(
    var value1: A,
    var value2: B,
) : Serializable
    where A : Serializable,
          B : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if ((other == null) || Hibernate.getClass(this) != Hibernate.getClass(other)) {
            return false
        }

        other as CompositeId<*, *>

        return value1 == other.value1
            && value2 == other.value2
    }

    override fun hashCode(): Int = Objects.hash(value1, value2)

    override fun toString(): String = "CompositeId(value1=$value1, value2=$value2)"

    companion object {
        private const val serialVersionUID = -7347621136985308862L
    }
}