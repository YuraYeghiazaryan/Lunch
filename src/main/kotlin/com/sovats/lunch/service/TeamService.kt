package com.sovats.lunch.service

import com.sovats.lunch.model.UserRole
import com.sovats.lunch.persistence.entity.Team
import com.sovats.lunch.persistence.entity.TeamMember
import com.sovats.lunch.persistence.repository.TeamMemberRepository
import com.sovats.lunch.persistence.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Service
class TeamService(
    private val teamRepository: TeamRepository,
    private val teamMemberRepository: TeamMemberRepository
) {
    @Transactional
    fun createTeam(name: String, createdByUserId: Long): Team {
        val team: Team = teamRepository.insert(name, createdByUserId)
        /** Team creator become ADMIN by default */
        this.addTeamMember(team.id, createdByUserId, UserRole.ADMIN)

        return team
    }

    fun addTeamMember(teamId: Long, userId: Long, role: UserRole?) {
        try {
            /* fixme: add default value generation support by providing null value */
            if (role == null) {
                teamMemberRepository.insert(teamId, userId)
            } else {
                teamMemberRepository.insert(teamId, userId, role.name)
            }
        } catch (exception: SQLException) {
            println(exception.message)
        }
    }

    @Transactional
    fun removeTeamMembers(teamId: Long, userIds: List<Long>) {
        if (!teamRepository.existsById(teamId)) {
            throw IllegalStateException("Can't remove users from team by id $teamId. Team doesn't exist")
        }
        teamMemberRepository.deleteByTeamIdAndUserIdIn(teamId, userIds)
    }

    fun setTeamMemberRole(teamId: Long, userId: Long, role: UserRole): TeamMember  {
        return teamMemberRepository.updateRole(teamId, userId, role.name)
    }

    fun editTeamDetails(teamId: Long, newName: String) {
        teamRepository.updateNameTeamDetails(teamId, newName)
    }

    fun deleteTeam(teamId: Long) {
        teamRepository.deleteById(teamId)
    }
}
