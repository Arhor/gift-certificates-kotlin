package com.epam.esm.gift.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditable<T> : AbstractIdentifiable<T>(), Auditable<Instant>
    where T : Serializable {

    @CreatedDate
    @Column(name = "date_time_created")
    override var dateTimeCreated: Instant? = null

    @LastModifiedDate
    @Column(name = "date_time_updated")
    override var dateTimeUpdated: Instant? = null
}