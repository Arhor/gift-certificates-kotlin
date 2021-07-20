package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Tag

interface TagRepository : BaseRepository<Tag, Long> {

    fun findTagByName(name: String): Tag?

    fun findTagByNames(names: List<String>): List<Tag>

    fun findTagsByCertificateId(certificateId: Long): List<Tag>

    fun addTagsToCertificate(certificateId: Long, tags: List<Tag>)

    fun removeTagsFromCertificate(certificateId: Long, tags: List<Tag>)
}