package com.epam.esm.gift.model

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
@Table(name = "certificates")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE certificates SET deleted = true WHERE id = ?")
open class Certificate : AbstractDeletable<Long>() {

    @Column(name = "name")
    open var name: String? = null

    @Column(name = "description")
    open var description: String? = null

    @Column(name = "price")
    open var price: BigDecimal? = null

    @Column(name = "duration")
    open var duration: Int? = null

    @CreatedDate
    @Column(name = "date_time_created")
    override var dateTimeCreated: Instant? = null

    @LastModifiedDate
    @Column(name = "date_time_updated")
    override var dateTimeUpdated: Instant? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "certificates_has_tags",
        joinColumns = [JoinColumn(name = "certificates_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "tags_id", nullable = false)],
    )
    open var tags: MutableList<Tag> = ArrayList()
}