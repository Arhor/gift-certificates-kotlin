package com.epam.esm.gift.web.api

import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.service.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}