package com.sovats.lunch.transport

import com.sovats.lunch.api.TeamApi
import com.sovats.lunch.model.TeamDetailsDto
import com.sovats.lunch.model.TeamDto
import com.sovats.lunch.model.UserIdsDto
import com.sovats.lunch.model.UserRole
import com.sovats.lunch.model.UserRoleDto
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

    override fun createTeam(xUserId: Long, teamDetailsDto: TeamDetailsDto): ResponseEntity<TeamDto> {
        val team: Team = teamService.createTeam(
            teamDetailsDto.name,
            xUserId
        )

        val teamDto: TeamDto = this.conversionService.convert(team, TeamDto::class.java)
            ?: throw IllegalArgumentException("Team $team can't be converted to the TeamDto.")

        return ResponseEntity.status(201).body(teamDto)
    }

    override fun addTeamMembers(xUserId: Long, teamId: Long, userIdsDto: UserIdsDto): ResponseEntity<Unit> {
        // TODO xUserId
        // TODO userIds or memberIds
        teamService.addTeamMembers(userIdsDto.userIds, teamId)
        return ResponseEntity.ok().build()
    }

    override fun removeTeamMembers(xUserId: Long, teamId: Long, userIdsDto: UserIdsDto): ResponseEntity<Unit> {
        // TODO xUserId
        // TODO userIds or memberIds
        teamService.removeTeamMembers(userIdsDto.userIds, teamId)
        return ResponseEntity.ok().build()
    }

    override fun setTeamMemberRole(xUserId: Long, teamId: Long, memberId: Long, roleDto: UserRoleDto): ResponseEntity<Unit> {
        //TODO xUserId
        // TODO userId or memberId
        // TODO roleDto
        val role: UserRole = conversionService.convert(roleDto, UserRole::class.java)
            ?: throw IllegalArgumentException("Invalid role: $roleDto")

        teamService.setTeamMemberRole(memberId, teamId, role)
        return ResponseEntity.ok().build()
    }

    override fun editTeamDetails(xUserId: Long, teamId: Long, teamDetailsDto: TeamDetailsDto): ResponseEntity<Unit> {
        //TODO xUserId
        teamService.editTeamDetails(teamId, teamDetailsDto.name)
        return ResponseEntity.ok().build()
    }

    override fun deleteTeam(xUserId: Long, teamId: Long): ResponseEntity<Unit> {
        //TODO xUserId
        teamService.deleteTeam(teamId)
        return ResponseEntity.ok().build()
    }
}
