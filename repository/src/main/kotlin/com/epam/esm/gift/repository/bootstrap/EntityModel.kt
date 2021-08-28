package com.epam.esm.gift.repository.bootstrap

data class EntityModel(
    val tableName: String,
    val idColumn: ColumnProperty,
    val restColumns: List<ColumnProperty>,
)
