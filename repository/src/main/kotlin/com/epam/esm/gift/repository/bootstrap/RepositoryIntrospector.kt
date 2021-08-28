package com.epam.esm.gift.repository.bootstrap

import com.epam.esm.gift.annotation.Column
import com.epam.esm.gift.annotation.Id
import com.epam.esm.gift.annotation.Table
import com.epam.esm.gift.repository.impl.AbstractRepository
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.starProjectedType

@Component
class RepositoryIntrospector {

    private val modelByType: MutableMap<KClass<*>, EntityModel> = ConcurrentHashMap()

    fun <T : AbstractRepository<*, *>> introspect(repositoryClass: KClass<T>): EntityModel {

        val entityClass = determineEntityClass(repositoryClass)

        return modelByType.computeIfAbsent(entityClass, ::computeEntityModel)
    }

    private fun computeEntityModel(entityClass: KClass<*>): EntityModel {
        val tableName = extractTableName(entityClass)

        val columnProperties = entityClass.members.filter { it.hasAnnotation<Column>() }

        val idColumn =
            columnProperties.firstOrNull { it.hasAnnotation<Id>() }?.let(this::introspectColumnProperty)
                ?: throw IllegalStateException("Cannot find property annotated with Id")

        val restColumns =
            columnProperties.filter { !it.hasAnnotation<Id>() }.map(this::introspectColumnProperty)

        return EntityModel(tableName, idColumn, restColumns)
    }

    private fun extractTableName(entityClass: KClass<*>): String {
        return entityClass.findAnnotation<Table>()?.name
            ?: entityClass.simpleName
            ?: throw IllegalStateException("Cannot determine table name")
    }

    private fun introspectColumnProperty(property: KCallable<*>): ColumnProperty {
        val propName = property.name
        val realName = property.findAnnotation<Column>()?.name?.takeIf { it.isNotBlank() } ?: propName
        return ColumnProperty(propName, realName)
    }

    private fun <T : AbstractRepository<*, *>> determineEntityClass(repositoryClass: KClass<T>): KClass<*> {
        return repositoryClass.supertypes.firstOrNull(this::isRepositoryType)
            ?.arguments
            ?.get(ENTITY_TYPE_ARG_IDX)
            ?.type
            ?.classifier as? KClass<*>
            ?: throw IllegalStateException("Cannot determine entity class")
    }

    private fun isRepositoryType(type: KType): Boolean {
        return type.classifier == AbstractRepository::class.starProjectedType.classifier
    }

    companion object {
        private const val ENTITY_TYPE_ARG_IDX = 0
    }
}