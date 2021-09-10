package com.epam.esm.gift.web.api

import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.service.Service
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class TagControllerImpl(private val service: Service<TagDTO, Long>) : TagController {

    override fun getTags(page: Int, size: Int): CollectionModel<*> {
//        PagedModel.of(service.findPaged(page, size), PagedModel.PageMetadata(page, size))


        return         CollectionModel.of(service.findPaged(page, size).map(::convert))

    }

    override fun getTagById(tagId: Long): EntityModel<TagDTO> {
        return service.findOne(tagId).let(::convert)
    }

    override fun createNewTag(tag: TagDTO): ResponseEntity<*> {
        val createdTag = service.create(tag)

        val location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(createdTag.id)

        return ResponseEntity.created(location).body(createdTag)
    }

    override fun deleteTag(@PathVariable tagId: Long) {
        service.deleteById(tagId)
    }

    private fun convert(dto: TagDTO): EntityModel<TagDTO> {

        object : RepresentationModel<Nothing>() {

        }

        return EntityModel.of(
            dto,
            linkTo<TagControllerImpl> { getTagById(dto.id!!) }.withSelfRel(),
            linkTo<TagControllerImpl> { getTags(1, 20) }.withRel("tags"),
        )
    }
}