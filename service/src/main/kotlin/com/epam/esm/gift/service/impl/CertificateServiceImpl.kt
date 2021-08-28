package com.epam.esm.gift.service.impl

import com.epam.esm.gift.converter.CertificateConverter
import com.epam.esm.gift.converter.TagConverter
import com.epam.esm.gift.dto.CertificateDTO
import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.error.EntityNotFoundException
import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.CertificateRepository
import com.epam.esm.gift.repository.TagRepository
import com.epam.esm.gift.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service as SpringService

@Transactional
@SpringService
class CertificateServiceImpl(
    private val certificateRepository: CertificateRepository,
    private val certificateConverter: CertificateConverter,
    private val tagRepository: TagRepository,
    private val tagConverter: TagConverter,
) : Service<CertificateDTO, Long> {

    @Lazy
    @Autowired
    lateinit var self: Service<CertificateDTO, Long>

    override fun findOne(id: Long): CertificateDTO {
        return certificateRepository.findById(id)
            ?.let(certificateConverter::mapEntityToDto)
            ?.let(::initializeCertificateTags)
            ?: throw EntityNotFoundException("certificate", "id = $id")
    }

    override fun findAll(): List<CertificateDTO> {
        return certificateRepository.findAll()
            .map(certificateConverter::mapEntityToDto)
            .map(::initializeCertificateTags)
    }

    override fun create(item: CertificateDTO): CertificateDTO {
        val certificate = certificateConverter.mapDtoToEntity(item)
        val createdCertificate = certificateRepository.create(certificate.copy())

        return certificateConverter.mapEntityToDto(createdCertificate).apply {
            tags = linkTagsToCertificate(id, item.tags)
        }
    }

    override fun update(item: CertificateDTO): CertificateDTO {
        val certificate = certificateConverter.mapDtoToEntity(item)
        val updatedCertificate = certificateRepository.update(certificate.copy())

        tagRepository.removeAllTagsFromCertificate(certificate.id!!)

        return certificateConverter.mapEntityToDto(updatedCertificate).apply {
            tags = linkTagsToCertificate(id, item.tags)
        }
    }

    override fun deleteById(id: Long) {
        certificateRepository.findById(id)?.let(certificateRepository::delete)
            ?: throw EntityNotFoundException("certificate", "id = $id")
    }

    private fun initializeCertificateTags(certificate: CertificateDTO): CertificateDTO {
        val certificateTags = tagRepository.findTagsByCertificateId(certificate.id!!)
        certificate.tags = certificateTags.map(tagConverter::mapEntityToDto)
        return certificate
    }

    private fun linkTagsToCertificate(certificateId: Long?, tags: List<TagDTO>?): List<TagDTO> {
        if ((certificateId != null) && (tags != null) && tags.isNotEmpty()) {
            val tagNames = tags.mapNotNull(TagDTO::name)
            val existingTags = tagRepository.findTagByNames(tagNames)
            val existingTagNames = existingTags.map(Tag::name)

            val createdTags =
                tags.filter { !existingTagNames.contains(it.name) }
                    .map(tagConverter::mapDtoToEntity)
                    .map(tagRepository::create)

            val certificateTags = existingTags + createdTags

            tagRepository.addTagsToCertificate(certificateId, certificateTags)

            return certificateTags.map(tagConverter::mapEntityToDto)
        }
        return emptyList()
    }
}