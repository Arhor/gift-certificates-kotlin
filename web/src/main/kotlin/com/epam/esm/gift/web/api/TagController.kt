package com.epam.esm.gift.web.api

import com.epam.esm.gift.dto.TagDTO
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/tags"])
interface TagController {

    @GetMapping
    fun getTags(
        @RequestParam(defaultValue = DEFAULT_PAGE) page: Int,
        @RequestParam(defaultValue = DEFAULT_SIZE) size: Int,
    ): CollectionModel<*>

    @GetMapping("/{tagId}")
    fun getTagById(@PathVariable tagId: Long): EntityModel<TagDTO>

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewTag(@RequestBody tag: TagDTO): ResponseEntity<*>

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTag(@PathVariable tagId: Long)

    companion object {
        const val DEFAULT_PAGE = "1"
        const val DEFAULT_SIZE = "20"
    }
}