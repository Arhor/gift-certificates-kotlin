package com.epam.esm.gift.repository

import com.epam.esm.gift.model.User

interface UserRepository : Repository<User, Long> {

    fun findTagByUsername(username: String): User?
}