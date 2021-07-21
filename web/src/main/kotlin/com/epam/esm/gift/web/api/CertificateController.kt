package com.epam.esm.gift.web.api

import com.epam.esm.gift.dto.CertificateDTO
import com.epam.esm.gift.service.Service
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCertificate(@RequestBody certificate: CertificateDTO): ResponseEntity<CertificateDTO> {
        val createdCertificate = service.create(certificate)

        val location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(createdCertificate.id)

        return ResponseEntity.created(location).body(createdCertificate)
    }

    @DeleteMapping("/{certificateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCertificate(@PathVariable certificateId: Long) {
        service.deleteById(certificateId)
    }
}