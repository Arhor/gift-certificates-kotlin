package com.epam.esm.gift.repository.impl

import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.TagRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class TagRepositoryImpl : AbstractRepository<Tag, Long>(), TagRepository {

    @PersistenceContext
    override lateinit var entityManager: EntityManager

    override val entityClass = Tag::class.java

    override fun findTagByName(name: String): Tag? {
        return entityManager.createQuery(SELECT_BY_NAME, Tag::class.java)
            .setParameter("name", name)
            .resultList
            .firstOrNull()
    }

    override fun findTagByNames(names: List<String>): List<Tag> {
        return entityManager.createQuery(SELECT_BY_NAMES, Tag::class.java)
            .setParameter("names", names)
            .resultList
    }

    override fun addTagsToCertificate(certificateId: Long, tags: List<Tag>) {
        if (tags.isEmpty()) {
            return
        }

        val params = mutableMapOf("certificateId" to certificateId)

        val query = StringJoiner(
            ", ",
            "INSERT INTO certificates_has_tags (certificates_id, tags_id) VALUES ",
            ""
        )

        for ((index, tag) in tags.withIndex()) {
            val tagIdPlaceholder = "tag$index"
            query.add("(:certificateId, :$tagIdPlaceholder)")
            params[tagIdPlaceholder] = tag.id!!
        }

        val nativeQuery = entityManager.createNativeQuery(query.toString())

        for ((param, value) in params) {
            nativeQuery.setParameter(param, value)
        }

        nativeQuery.executeUpdate()
    }

    override fun removeAllTagsFromCertificate(certificateId: Long) {
        entityManager
            .createNativeQuery(REMOVE_TAGS_FROM_CERTIFICATE)
            .setParameter("certificateId", certificateId)
            .executeUpdate()
    }

    companion object {
        const val SELECT_BY_NAME =
            "SELECT t FROM Tag t WHERE t.name = :name"
        const val SELECT_BY_NAMES =
            "SELECT t FROM Tag t WHERE t.name IN (:names)"
        const val REMOVE_TAGS_FROM_CERTIFICATE =
            "DELETE FROM certificates_has_tags WHERE certificates_id = :certificateId"
    }
}