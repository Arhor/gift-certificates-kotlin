package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Tag

interface TagRepository : Repository<Tag, Long> {

    fun findTagByName(name: String): Tag?

    fun findTagByNames(names: List<String>): List<Tag>

    fun addTagsToCertificate(certificateId: Long, tags: List<Tag>)

    fun removeAllTagsFromCertificate(certificateId: Long)
}