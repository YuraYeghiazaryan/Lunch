package com.sovats.lunch.service

import com.sovats.lunch.persistence.entity.Team
import com.sovats.lunch.persistence.entity.User
import com.sovats.lunch.persistence.repository.TeamRepository
import com.sovats.lunch.persistence.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TeamService(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
) {
    fun createTeam(name: String, createdByUserId: Long): Team {
        val user: User =
            this.userRepository.findUserById(createdByUserId)
                ?: throw IllegalStateException("Can't create team for user by id ${createdByUserId}, User doesn't exists");

        val team = Team(
            name = name,
            createdByUser = user,
            teamMembers = mutableSetOf(),
        )
        return teamRepository.save(team)
    }
}
