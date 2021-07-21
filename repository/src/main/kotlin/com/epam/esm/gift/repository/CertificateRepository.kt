package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.bootstrap.ColumnProperty

interface CertificateRepository : Repository<Certificate, Long> {

    fun updatePartially(certificateId: Long, diffs: List<Pair<ColumnProperty, Any?>>)
}