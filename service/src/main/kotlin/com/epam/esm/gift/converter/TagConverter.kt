package com.epam.esm.gift.converter

import com.epam.esm.gift.config.MapStructConfig
import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.model.Tag
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(config = MapStructConfig::class)
interface TagConverter : Converter<Tag, TagDTO> {

    override fun mapDtoToEntity(item: TagDTO): Tag

    @InheritInverseConfiguration
    override fun mapEntityToDto(item: Tag): TagDTO
}