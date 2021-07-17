package com.epam.esm.gift.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class Column(val name: String = "")
