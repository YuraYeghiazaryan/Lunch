package com.sovats.lunch.service

import com.sovats.lunch.model.UserRole
import com.sovats.lunch.persistence.entity.Team
import com.sovats.lunch.persistence.entity.TeamMember
import com.sovats.lunch.persistence.entity.User
import com.sovats.lunch.persistence.repository.TeamMemberRepository
import com.sovats.lunch.persistence.repository.TeamRepository
import com.sovats.lunch.persistence.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Service
class TeamService(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
    private val teamMemberRepository: TeamMemberRepository
) {
    @Transactional
    fun createTeam(name: String, createdByUserId: Long): Team {
        val user: User =
            this.userRepository.findUserById(createdByUserId)
                ?: throw IllegalStateException("Can't create team for user by id ${createdByUserId}. User doesn't exists");

        val team: Team = teamRepository.insert(name, createdByUserId)
        this.addTeamMember(team.id, user.id, UserRole.ADMIN) // Team creator become ADMIN by default

        return team
    }

    fun addTeamMember(teamId: Long, userId: Long, role: UserRole) {
        try {
            teamMemberRepository.insert(teamId, userId, role.name)
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
