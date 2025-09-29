package com.sovats.lunch.service

import com.sovats.lunch.model.UserRole
import com.sovats.lunch.persistence.entity.Team
import com.sovats.lunch.persistence.entity.TeamMember
import com.sovats.lunch.persistence.entity.User
import com.sovats.lunch.persistence.repository.TeamMemberRepository
import com.sovats.lunch.persistence.repository.TeamRepository
import com.sovats.lunch.persistence.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TeamService(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
    private val teamMemberRepository: TeamMemberRepository
) {
    fun createTeam(name: String, createdByUserId: Long): Team {
        val user: User =
            this.userRepository.findUserById(createdByUserId)
                ?: throw IllegalStateException("Can't create team for user by id ${createdByUserId}. User doesn't exists");

        val team: Team = teamRepository.save(Team(
            name = name,
            createdByUser = user,
            teamMembers = mutableSetOf(),
        ))

        addUserToTeam(user, team, UserRole.ADMIN) // Team creator become ADMIN by default

        return team
    }

    fun addUserToTeam(user: User, team: Team, role: UserRole): TeamMember {
        val teamMember: TeamMember = TeamMember(
            team,
            user,
            role
        )
        return teamMemberRepository.save(teamMember)
    }

    fun addUserToTeam(userId: Long, teamId: Long, role: UserRole): TeamMember {
        val user: User = userRepository.findUserById(userId)
            ?: throw IllegalStateException("Can't add user by id $userId to the team by id $teamId. User doesn't exists")

        val team: Team = teamRepository.findTeamById(teamId)
            ?: throw IllegalStateException("Can't add user by id $userId to the team by id $teamId. Team doesn't exists")
        return addUserToTeam(user, team, role)
    }

    fun setTeamMemberRole(userId: Long, teamId: Long, role: UserRole): TeamMember  {
        val teamMember: TeamMember = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
            ?: throw IllegalStateException("Can't set role $role for user. Can't find user by id $userId and team by id $teamId.")

        teamMember.role = role
        return teamMemberRepository.save(teamMember)
    }

    fun removeUserFromTeam(userId: Long, teamId: Long) {
        teamMemberRepository.deleteByTeamIdAndUserId(teamId, userId)
    }
}
