package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Certificate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CertificateRepositoryImpl(rowMapper: RowMapper<Certificate>) :
    AbstractBaseRepository<Certificate, Long>(rowMapper), CertificateRepository