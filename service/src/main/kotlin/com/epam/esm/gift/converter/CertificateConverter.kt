package com.epam.esm.gift.converter

import com.epam.esm.gift.config.MapStructConfig
import com.epam.esm.gift.dto.CertificateDTO
import com.epam.esm.gift.model.Certificate
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(
    config = MapStructConfig::class,
    uses = [TagConverter::class],
)
interface CertificateConverter : Converter<Certificate, CertificateDTO> {

    override fun mapDtoToEntity(item: CertificateDTO): Certificate

    @InheritInverseConfiguration
    override fun mapEntityToDto(item: Certificate): CertificateDTO
}