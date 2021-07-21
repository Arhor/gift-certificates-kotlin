package com.epam.esm.gift.web.api

import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.service.Service
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/tags")
class TagController(private val service: Service<TagDTO, Long>) {

    @GetMapping
    fun getAllTags(): List<TagDTO> {
        return service.findAll()
    }

    @GetMapping("/{tagId}")
    fun getTagById(@PathVariable tagId: Long): TagDTO {
        return service.findOne(tagId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewTag(@RequestBody tag: TagDTO): ResponseEntity<*> {
        val createdTag = service.create(tag)

        val location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(createdTag.id)

        return ResponseEntity.created(location).body(createdTag)
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTag(@PathVariable tagId: Long) {
        service.deleteById(tagId)
    }
}