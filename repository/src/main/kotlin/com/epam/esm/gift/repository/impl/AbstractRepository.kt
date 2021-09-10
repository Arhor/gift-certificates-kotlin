package com.epam.esm.gift.repository.impl

import com.epam.esm.gift.model.Deletable
import com.epam.esm.gift.model.Identifiable
import com.epam.esm.gift.repository.Repository
import mu.KLogging
import java.io.Serializable
import javax.persistence.EntityManager

abstract class AbstractRepository<T, K> : Repository<T, K>
    where T : Identifiable<K>,
          K : Serializable {

    protected abstract val entityManager: EntityManager
    protected abstract val entityClass: Class<T>

    override fun create(entity: T): T {
        entityManager.persist(entity)
        return entity
    }

    override fun update(entity: T): T {
        return when (entity.id) {
            null -> {
                entityManager.persist(entity)
                entity
            }
            else -> {
                entityManager.merge(entity)
            }
        }
    }

    override fun findAll(): List<T> {
        return with(entityManager.criteriaBuilder.createQuery(entityClass)) {
            entityManager.createQuery(select(from(entityClass))).resultList
        }
    }

    override fun findById(id: K): T? {
        return entityManager.find(entityClass, id)
    }

    override fun delete(entity: T) {
        if (entity is Deletable) {
            entityManager.merge(entity.apply { deleted = true })
        } else {
            entityManager.remove(entity)
        }
    }

    companion object : KLogging()
}