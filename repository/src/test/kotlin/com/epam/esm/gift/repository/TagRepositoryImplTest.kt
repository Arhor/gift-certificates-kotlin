package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.impl.CertificateRepositoryImpl
import com.epam.esm.gift.repository.impl.TagRepositoryImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@Transactional
@ExtendWith(SpringExtension::class)
@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(classes = [TestDatabaseConfig::class])
internal class TagRepositoryImplTest {

    @Autowired
    private lateinit var repository: TagRepositoryImpl

    @Autowired
    private lateinit var certificateRepository: CertificateRepositoryImpl

    @Test
    fun `should correctly persist and find new tag`() {
        // given
        val initialTag = Tag().apply { name = "Test tag" }

        // when
        val createdTagId = repository.create(initialTag).id
        val tagFromRepository = repository.findById(createdTagId!!)

        // then
        assertThat(tagFromRepository)
            .isNotNull
            .isEqualTo(initialTag)
    }

    @Test
    fun `should correctly update an existing tag`() {
        // given
        val initialTag = Tag().apply { name = "Test tag" }
        val updatedName = "Updated test tag name"

        // when
        val createdTag = repository.create(initialTag)
        val updatedTag = repository.update(createdTag.apply { name = updatedName })

        // then
        assertThat(updatedTag.name)
            .isEqualTo(createdTag.name)
            .isEqualTo(updatedName)
    }

    @Test
    fun `readAll should return list of all existing tags`() {
        // given
        val initialTags = listOf(
            Tag().apply { name = "Test tag #1" },
            Tag().apply { name = "Test tag #2" },
            Tag().apply { name = "Test tag #3" },
        )

        for (initialTag in initialTags) {
            repository.create(initialTag)
        }

        // when
        val allExistingTags = repository.findAll()

        // then
        assertThat(allExistingTags)
            .hasSameSizeAs(initialTags)
            .containsAll(initialTags)
    }

    @Test
    fun `should delete existing tag`() {
        // given
        val initialTag = Tag().apply { name = "Test tag" }

        // when
        val createdTag = repository.create(initialTag)
        repository.delete(createdTag)
        val tagFromRepository = repository.findById(createdTag.id!!)

        // then
        assertThat(tagFromRepository)
            .isNull()
    }

    @Test
    fun `should delete existing tag by its id`() {
        // given
        val initialTag = Tag().apply { name = "Test tag" }

        // when
        val createdTag = repository.create(initialTag)
        repository.delete(createdTag)
        val tagFromRepository = repository.findById(createdTag.id!!)

        // then
        assertThat(tagFromRepository)
            .isNull()
    }

//    @Test
//    fun `should correctly add tags to an existing certificate`() {
//        // given
//        val initialTags = listOf(
//            Tag(name = "Test tag #1"),
//            Tag(name = "Test tag #2"),
//            Tag(name = "Test tag #3"),
//        )
//
//        for (initialTag in initialTags) {
//            repository.create(initialTag)
//        }
//
//        val certificate = certificateRepository.create(
//            Certificate(
//                name = "Test certificate name",
//                description = "Test certificate description",
//                price = BigDecimal("1.00"),
//                duration = 30
//            )
//        )
//
//        // when
//        repository.addTagsToCertificate(certificate.id!!, initialTags)
//        val tagsByCertificateId = repository.findTagsByCertificateId(certificate.id!!)
//
//        // then
//        assertThat(tagsByCertificateId)
//            .isEqualTo(initialTags)
//    }

//    @Test
//    fun `should correctly remove tags from an existing certificate`() {
//        // given
//        val initialTags = listOf(
//            Tag(name = "Test tag #1"),
//            Tag(name = "Test tag #2"),
//            Tag(name = "Test tag #3"),
//        )
//
//        initialTags.forEach(repository::create)
//
//        val certificate = certificateRepository.create(
//            Certificate(
//                name = "Test certificate name",
//                description = "Test certificate description",
//                price = BigDecimal("1.00"),
//                duration = 30
//            )
//        )
//
//        // when
//        repository.addTagsToCertificate(certificate.id!!, initialTags)
//        repository.removeAllTagsFromCertificate(certificate.id!!)
//        val tagsByCertificateId = repository.findTagsByCertificateId(certificate.id!!)
//
//        // then
//        assertThat(tagsByCertificateId)
//            .isEmpty()
//    }
}