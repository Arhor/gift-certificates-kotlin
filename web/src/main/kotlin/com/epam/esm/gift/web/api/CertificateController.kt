package com.epam.esm.gift.web.api

import com.epam.esm.gift.dto.CertificateDTO
import com.epam.esm.gift.service.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/certificates")
class CertificateController(private val service: Service<CertificateDTO, Long>) {

    @GetMapping
    fun getAllTags(): List<CertificateDTO> {
        return service.findAll()
    }

    @GetMapping("/{certificateId}")
    fun getTagById(@PathVariable certificateId: Long): CertificateDTO {
        return service.findOne(certificateId)
    }
}