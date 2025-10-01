package com.sovats.lunch.convertor

import com.sovats.lunch.model.TeamDto
import com.sovats.lunch.persistence.entity.Team
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.ZoneOffset

@Component
class TeamToDtoConverter() : Converter<Team, TeamDto> {

    override fun convert(from: Team): TeamDto {
        return TeamDto(
            id = from.id!!,
            name = from.name,
            createdByUserId = from.createdByUser.id!!,
            createdAt = from.createdAt!!.toLocalDateTime().atOffset(ZoneOffset.UTC),
        )
    }
}
