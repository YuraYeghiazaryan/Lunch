package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.TeamMember
import com.sovats.lunch.persistence.entity.TeamMemberId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamMemberRepository: JpaRepository<TeamMember, TeamMemberId> {
    fun findByTeamIdAndUserId(teamId: Long, userId: Long): TeamMember?
    fun findByTeamId(teamId: Long): List<TeamMember>
    fun deleteByTeamIdAndUserId(teamId: Long, userId: Long)
}