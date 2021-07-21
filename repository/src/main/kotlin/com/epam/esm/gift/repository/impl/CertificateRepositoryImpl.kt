package com.epam.esm.gift.repository.impl

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.CertificateRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CertificateRepositoryImpl(rowMapper: RowMapper<Certificate>) :
    AbstractRepository<Certificate, Long>(rowMapper), CertificateRepository