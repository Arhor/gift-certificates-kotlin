package com.epam.esm.gift.repository.impl

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.CertificateRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CertificateRepositoryImpl : AbstractRepository<Certificate, Long>(), CertificateRepository {

  @PersistenceContext
  override lateinit var entityManager: EntityManager

  override val entityClass = Certificate::class.java
}