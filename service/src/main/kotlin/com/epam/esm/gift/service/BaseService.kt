package com.epam.esm.gift.service

import java.io.Serializable

interface Service<D, K> where K : Serializable {

    fun findOne(id: K): D

    fun findAll(): List<D>

    fun findPaged(page: Int, size: Int): List<D> {
        return findAll()
            .asSequence()
            .drop((page - 1) * size)
            .take(size)
            .toList()
    }

    fun create(item: D): D

    fun update(item: D): D

    fun deleteById(id: K)
}