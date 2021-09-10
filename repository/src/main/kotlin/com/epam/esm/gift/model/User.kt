package com.epam.esm.gift.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
open class User : AbstractAuditable<Long>() {

    @Column(name = "username")
    open var username: String? = null

    @Column(name = "password")
    open var password: String? = null

    @Column(name = "role")
    open var role: String? = null

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    open var orders: MutableList<Order> = ArrayList()
}