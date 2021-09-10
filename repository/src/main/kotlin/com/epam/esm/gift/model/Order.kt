package com.epam.esm.gift.model

import javax.persistence.*

@Entity
@Table(name = "orders")
open class Order : AbstractAuditable<Long>() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    open var user: User? = null

    @OneToMany(mappedBy = "order")
    open var items: MutableList<OrderItem> = ArrayList()
}