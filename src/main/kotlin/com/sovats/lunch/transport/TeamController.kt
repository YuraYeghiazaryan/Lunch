package com.sovats.lunch.transport

import com.sovats.lunch.api.TeamApi
import com.sovats.lunch.model.CreateTeamRequest
import com.sovats.lunch.model.TeamDto
import com.sovats.lunch.persistence.entity.Team
import com.sovats.lunch.service.TeamService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class TeamController (
    private val teamService: TeamService,
    private val conversionService: ConversionService
): TeamApi {

    override fun createTeam(xUserId: Long, createTeamRequest: CreateTeamRequest): ResponseEntity<TeamDto> {
        val team: Team = teamService.createTeam(
            createTeamRequest.name,
            xUserId
        )

        val teamDto: TeamDto = this.conversionService.convert(team, TeamDto::class.java)
            ?: throw IllegalArgumentException("Team $team can't be converted to the TeamDto.")

        return ResponseEntity.status(201).body(teamDto)
    }
}
