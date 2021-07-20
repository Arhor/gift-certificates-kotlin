package com.epam.esm.gift.service.impl

import com.epam.esm.gift.converter.Converter
import com.epam.esm.gift.dto.CertificateDTO
import com.epam.esm.gift.error.EntityNotFoundException
import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.BaseRepository
import com.epam.esm.gift.repository.TagRepository
import com.epam.esm.gift.service.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service as SpringService

@Transactional
@SpringService
class CertificateServiceImpl(
    private val certificateRepository: BaseRepository<Certificate, Long>,
    private val certificateConverter: Converter<Certificate, CertificateDTO>,
    private val tagRepository: TagRepository,
) : Service<CertificateDTO, Long> {

    override fun findOne(id: Long): CertificateDTO {
        return certificateRepository.findById(id)
            ?.let(::initializeCertificateTags)
            ?.let(certificateConverter::mapEntityToDto)
            ?: throw EntityNotFoundException("certificate", "id = $id")
    }

    override fun findAll(): List<CertificateDTO> {
        return certificateRepository.findAll()
            .map(::initializeCertificateTags)
            .map(certificateConverter::mapEntityToDto)
    }

    override fun create(item: CertificateDTO): CertificateDTO {
        val certificate = certificateConverter.mapDtoToEntity(item)
        val createdCertificate = certificateRepository.create(certificate)

        certificate.tags.takeIf { it.isNotEmpty() }?.let { tags ->
            val tagNames = tags.mapNotNull(Tag::name)
            val existingTags = tagRepository.findTagByNames(tagNames)
            val existingTagNames = existingTags.map(Tag::name)
            val createdTags =
                tags.filter { tag -> !existingTagNames.contains(tag.name) }
                    .map(tagRepository::create)

            val certificateTags = existingTags + createdTags

            tagRepository.addTagsToCertificate(createdCertificate.id!!, certificateTags)

            createdCertificate.tags.addAll(certificateTags)
        }

        return certificateConverter.mapEntityToDto(createdCertificate)
    }

    override fun update(item: CertificateDTO): CertificateDTO {
        val certificate = certificateConverter.mapDtoToEntity(item)
        val updatedCertificate = certificateRepository.update(certificate)

        // TODO: update tags

        return certificateConverter.mapEntityToDto(updatedCertificate)
    }

    override fun deleteById(id: Long) {
        certificateRepository.findById(id)?.let(certificateRepository::delete)
            ?: throw EntityNotFoundException("certificate", "id = $id")
    }

    private fun initializeCertificateTags(certificate: Certificate): Certificate {
        val certificateTags = tagRepository.findTagsByCertificateId(certificate.id!!)
        certificate.tags.addAll(certificateTags)
        return certificate
    }
}