package com.epam.esm.gift.repository.bootstrap

import org.springframework.stereotype.Component

@Component
class QueryProvider {

    fun buildQueries(entityModel: EntityModel): Queries {

        val (tableName, idColumn, restColumns) = entityModel

        val restColumnsArray = restColumns.toTypedArray()
        val allColumns = arrayOf(idColumn, *restColumnsArray).joinToString { it.realName }

        val restColsRealNames = restColumns.joinToString { it.realName }
        val restColsPropNames = restColumns.joinToString { ":${it.propName}" }

        val idColToIdProp = colToProp(idColumn)
        val colsToProps = colsToProps(*restColumnsArray)

        return Queries(
            selectAll = "SELECT $allColumns FROM $tableName",
            selectOne = "SELECT $allColumns FROM $tableName WHERE $idColToIdProp",
            deleteOne = "DELETE FROM $tableName WHERE $idColToIdProp",
            insertOne = "INSERT INTO $tableName ($restColsRealNames) VALUES ($restColsPropNames)",
            updateOne = "UPDATE $tableName SET $colsToProps WHERE $idColToIdProp"
        )
    }

    private fun colsToProps(vararg columns: ColumnProperty): String {
        return columns.joinToString { colToProp(it) }
    }

    private fun colToProp(column: ColumnProperty): String {
        return "${column.realName} = :${column.propName}"
    }
}