package com.epam.esm.gift.repository

import com.epam.esm.gift.model.BaseEntity
import java.io.Serializable

interface BaseRepository<T, K> where T : BaseEntity<K>, K : Serializable {

    fun create(entity: T): T

    fun update(entity: T): T

    fun readAll(): List<T>

    fun readById(id: K): T?

    fun delete(entity: T)

    fun deleteById(id: K)
}