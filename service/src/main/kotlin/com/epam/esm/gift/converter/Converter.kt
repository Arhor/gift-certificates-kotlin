package com.epam.esm.gift.converter

interface Converter<A, B> {

    fun mapEntityToDto(item: A): B

    fun mapDtoToEntity(item: B): A
}