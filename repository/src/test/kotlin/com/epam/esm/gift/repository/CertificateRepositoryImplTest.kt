package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.impl.CertificateRepositoryImpl
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
internal class CertificateRepositoryImplTest {

    @Autowired
    private lateinit var repository: CertificateRepositoryImpl

    @Test
    fun `should correctly persist and find new certificate`() {
        // given
        val initialCertificate = testCertificate(name = "Test Certificate")

        // when
        val createdCertificateId = repository.create(initialCertificate).id
        val certificateFromRepository = repository.findById(createdCertificateId!!)

        // then
        assertThat(certificateFromRepository).isNotNull
        assertThat(certificateFromRepository?.name).isEqualTo(initialCertificate.name)
        assertThat(certificateFromRepository?.description).isEqualTo(initialCertificate.description)
        assertThat(certificateFromRepository?.price).isEqualTo(initialCertificate.price)
        assertThat(certificateFromRepository?.duration).isEqualTo(initialCertificate.duration)
    }

    @Test
    fun `should correctly update an existing certificate`() {
        // given
        val initialCertificate = testCertificate(name = "Test Certificate")
        val updatedName = "Updated test Certificate name"

        // when
        val createdCertificate = repository.create(initialCertificate)
        val updatedCertificate = repository.update(createdCertificate.apply { name = updatedName })

        // then
        assertThat(updatedCertificate.name)
            .isEqualTo(createdCertificate.name)
            .isEqualTo(updatedName)
    }

    @Test
    fun `readAll should return list of all existing certificates`() {
        // given
        val initialCertificates = listOf(
            testCertificate(name = "Test Certificate #1"),
            testCertificate(name = "Test Certificate #2"),
            testCertificate(name = "Test Certificate #3"),
        )

        for (initialCertificate in initialCertificates) {
            repository.create(initialCertificate)
        }

        // when
        val allExistingCertificates = repository.findAll().map { it.id }

        // then
        assertThat(allExistingCertificates)
            .hasSameSizeAs(initialCertificates)
            .containsAll(initialCertificates.map { it.id })
    }

    @Test
    fun `should delete existing certificate`() {
        // given
        val initialCertificate = testCertificate(name = "Test Certificate")

        // when
        val createdCertificate = repository.create(initialCertificate)
        repository.delete(createdCertificate)
        val certificateFromRepository = repository.findById(createdCertificate.id!!)

        // then
        assertThat(certificateFromRepository)
            .isNull()
    }

    @Test
    fun `should delete existing certificate by its id`() {
        // given
        val initialCertificate = testCertificate(name = "Test certificate")

        // when
        val createdCertificate = repository.create(initialCertificate)
        repository.delete(createdCertificate)
        val certificateFromRepository = repository.findById(createdCertificate.id!!)

        // then
        assertThat(certificateFromRepository)
            .isNull()
    }

    private fun testCertificate(name: String): Certificate {
        return Certificate().apply {
            this.name = name
            this.description = "Test certificate description"
            this.price = BigDecimal("1.00")
            this.duration = 30
        }
    }
}