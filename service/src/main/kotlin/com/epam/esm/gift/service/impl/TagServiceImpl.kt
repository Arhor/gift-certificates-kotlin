package com.epam.esm.gift.service.impl

import com.epam.esm.gift.converter.Converter
import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.error.EntityDuplicateException
import com.epam.esm.gift.error.EntityNotFoundException
import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.TagRepository
import com.epam.esm.gift.service.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service as SpringService

@Transactional
@SpringService
class TagServiceImpl(
    private val tagRepository: TagRepository,
    private val tagConverter: Converter<Tag, TagDTO>,
) : Service<TagDTO, Long> {

    override fun findOne(id: Long): TagDTO {
        return tagRepository.findById(id)?.let(tagConverter::mapEntityToDto)
            ?: throw EntityNotFoundException("tag", "id = $id")
    }

    override fun findAll(): List<TagDTO> {
        return tagRepository.findAll().map(tagConverter::mapEntityToDto)
    }

    override fun create(item: TagDTO): TagDTO {
        tagRepository.findTagByName(item.name)?.let {
            throw EntityDuplicateException(
                name = "tag",
                condition = "name = ${item.name}"
            )
        }
        val newTag = tagConverter.mapDtoToEntity(item)
        val createdTag = tagRepository.create(newTag)
        return tagConverter.mapEntityToDto(createdTag)
    }

    override fun update(item: TagDTO): TagDTO {
        tagRepository.findTagByName(item.name)?.let {
            throw EntityDuplicateException(
                name = "tag",
                condition = "name = ${item.name}"
            )
        }
        return tagConverter.mapDtoToEntity(item)
            .let(tagRepository::update)
            .let(tagConverter::mapEntityToDto)
    }

    override fun deleteById(id: Long) {
        tagRepository.findById(id)?.let(tagRepository::delete)
            ?: throw EntityNotFoundException("tag", "id = $id")
    }
}