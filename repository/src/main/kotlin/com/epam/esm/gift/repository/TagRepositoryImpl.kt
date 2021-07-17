package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Tag
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class TagRepositoryImpl(rowMapper: RowMapper<Tag>) : AbstractBaseRepository<Tag, Long>(rowMapper)