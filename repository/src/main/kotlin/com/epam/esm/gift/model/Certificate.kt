package com.epam.esm.gift.model

import com.epam.esm.gift.annotation.Column
import com.epam.esm.gift.annotation.Id
import com.epam.esm.gift.annotation.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

@Table("certificates")
data class Certificate(
    @Id
    @Column
    override var id: Long? = null,

    @Column
    var name: String? = null,

    @Column
    var description: String? = null,

    @Column
    var price: BigDecimal? = null,

    @Column
    var duration: Int? = null,

    @Column("date_time_created")
    var dateTimeCreated: LocalDateTime? = null,

    @Column("date_time_updated")
    var dateTimeUpdated: LocalDateTime? = null,
) : Entity<Long>, Auditable {

    override fun onCreate() {
        dateTimeCreated = LocalDateTime.now(ZoneOffset.UTC)
    }

    override fun onUpdate() {
        dateTimeUpdated = LocalDateTime.now(ZoneOffset.UTC)
    }
}