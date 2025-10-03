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

        val team: Team = teamRepository.save(Team(
            name = name,
            createdByUser = user,
            teamMembers = mutableSetOf(),
        ))

        addUserToTeam(user, team, UserRole.ADMIN) // Team creator become ADMIN by default

        return team
    }

    @Transactional
    fun addTeamMembers(userIds: List<Long>, teamId: Long) {
        // TODO userIds or memberIds
        val team: Team = teamRepository.findTeamById(teamId)
            ?: throw IllegalStateException("Can't add users to team by id $teamId. Team doesn't exist")

        userIds.forEach { userId ->
            val user = userRepository.findUserById(userId)
                ?: throw IllegalStateException("User $userId not found")

            // by default, newly added user role is USER // TODO GUEST role
            addUserToTeam(user, team, UserRole.USER)
        }
    }

    @Transactional
    fun removeTeamMembers(userIds: List<Long>, teamId: Long) {
        // TODO userIds or memberIds
        if (!teamRepository.existsById(teamId)) {
            throw IllegalStateException("Can't remove users from team by id $teamId. Team doesn't exist")
        }

        userIds.forEach { userId ->
            // Try to delete — if user is not in the team, this will just do nothing (no error)
            teamMemberRepository.deleteByTeamIdAndUserId(teamId, userId)
        }
    }

    fun setTeamMemberRole(userId: Long, teamId: Long, role: UserRole): TeamMember  {
        // TODO userId or memberId
        val teamMember: TeamMember = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
            ?: throw IllegalStateException("User $userId is not a member of team $teamId.")

        teamMember.role = role
        return teamMemberRepository.save(teamMember)
    }

    fun editTeamDetails(teamId: Long, newName: String) {
        val team: Team = teamRepository.findTeamById(teamId)
            ?: throw IllegalStateException("Can't edit team details by id $teamId. Team doesn't exist")

        team.name = newName
        teamRepository.save(team)
    }

    @Transactional
    fun deleteTeam(teamId: Long) {
        // TODO verify
        val teamMembers: List<TeamMember> = teamMemberRepository.findByTeamId(teamId)
        val userIds: List<Long> = teamMembers.map { it.user.id!! };
        removeTeamMembers(userIds, teamId)

        val team: Team = teamRepository.findTeamById(teamId)
        ?: throw IllegalStateException("Can't delete team by id $teamId. Team doesn't exist or already deleted")

        teamRepository.delete(team)
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
}
