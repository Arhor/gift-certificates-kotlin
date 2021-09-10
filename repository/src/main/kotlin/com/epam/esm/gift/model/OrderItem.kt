package com.epam.esm.gift.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "orders_has_certificates")
open class OrderItem {

    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "value1", column = Column(name = "order_id")),
        AttributeOverride(name = "value2", column = Column(name = "certificate_id"))
    )
    open var id: CompositeId<Long, Long>? = null

    @Column(name = "actual_price")
    open var actualPrice: BigDecimal? = null

    @Column(name = "amount")
    open var amount: Int? = null

    @ManyToOne(optional = false)
    @MapsId("order_id")
    open var order: Order? = null

    @ManyToOne(optional = false)
    @MapsId("certificate_id")
    open var certificate: Certificate? = null
}