package com.epam.esm.gift.model

import com.epam.esm.gift.annotation.Column
import com.epam.esm.gift.annotation.Id
import com.epam.esm.gift.annotation.Table

@Table("tags")
data class Tag(
    @Id
    @Column
    override var id: Long? = null,

    @Column
    var name: String? = null,
) : Entity<Long>