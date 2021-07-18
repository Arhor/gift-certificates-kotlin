package com.epam.esm.gift.model

interface Auditable {

    fun onCreate()

    fun onUpdate()
}