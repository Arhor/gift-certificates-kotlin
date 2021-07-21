package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Entity
import java.io.Serializable

interface Repository<T, K> where T : Entity<K>, K : Serializable {

    fun create(entity: T): T

    fun update(entity: T): T

    fun findAll(): List<T>

    fun findById(id: K): T?

    fun delete(entity: T)

    fun deleteById(id: K)
}