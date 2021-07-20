package com.epam.esm.gift.service.impl

import com.epam.esm.gift.converter.TagConverter
import com.epam.esm.gift.dto.TagDTO
import com.epam.esm.gift.error.EntityNotFoundException
import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.TagRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class TagServiceImplTest {

    @Mock
    private lateinit var tagRepository: TagRepository
    @Mock
    private lateinit var tagConverter: TagConverter

    @InjectMocks
    private lateinit var tagService: TagServiceImpl

    @Test
    fun `should return an existing tag represented as DTO`() {
        // given
        val id = 1L
        val name = "test"
        val tag = Tag(id, name)
        val tagDTO = TagDTO(id, name)

        `when`(tagRepository.findById(anyLong())).thenReturn(tag)
        `when`(tagConverter.mapEntityToDto(tag)).thenReturn(tagDTO)

        // when
        val result = tagService.findOne(1)

        // then
        assertThat(result)
            .isNotNull
            .isEqualTo(tagDTO)
    }

    @Test
    fun `should throw EntityNotFoundException`() {
        // given
        val id = 1L

        `when`(tagRepository.findById(anyLong())).thenReturn(null)

        // when
        val action = ThrowingCallable { tagService.findOne(id) }

        // then
        assertThatThrownBy(action)
            .isNotNull
            .isInstanceOf(EntityNotFoundException::class.java)
    }
}