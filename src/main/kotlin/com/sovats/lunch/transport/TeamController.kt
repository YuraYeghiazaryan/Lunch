package com.sovats.lunch.transport

import com.sovats.lunch.api.TeamApi
import com.sovats.lunch.model.AddTeamMemberDto
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

    override fun addTeamMembers(teamId: Long, addTeamMemberDto: List<AddTeamMemberDto>): ResponseEntity<Unit> {
        addTeamMemberDto.forEach { member ->
            teamService.addTeamMember(
                teamId = teamId,
                userId = member.userId,
                role = member.role?.let { conversionService.convert(member, UserRole::class.java) } ?: UserRole.USER
            )
        }

        return ResponseEntity.ok().build()
    }

    override fun removeTeamMembers(teamId: Long, userIdsDto: UserIdsDto): ResponseEntity<Unit> {
        teamService.removeTeamMembers(teamId, userIdsDto.userIds)
        return ResponseEntity.ok().build()
    }

    override fun setTeamMemberRole(teamId: Long, memberId: Long, roleDto: UserRoleDto): ResponseEntity<Unit> {
        val role: UserRole = conversionService.convert(roleDto, UserRole::class.java)
            ?: throw IllegalArgumentException("Invalid role: $roleDto")

        teamService.setTeamMemberRole(memberId, teamId, role)
        return ResponseEntity.ok().build()
    }

    override fun editTeamDetails(teamId: Long, teamDetailsDto: TeamDetailsDto): ResponseEntity<Unit> {
        teamService.editTeamDetails(teamId, teamDetailsDto.name)
        return ResponseEntity.ok().build()
    }

    override fun deleteTeam(teamId: Long): ResponseEntity<Unit> {
        teamService.deleteTeam(teamId)
        return ResponseEntity.ok().build()
    }
}
