package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.TeamMember
import com.sovats.lunch.persistence.entity.TeamMemberId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface TeamMemberRepository: JpaRepository<TeamMember, TeamMemberId> {
    @Query(
        """
            INSERT INTO team.team_members (team_id, user_id) 
            VALUES (:teamId, :userId)
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(teamId: Long, userId: Long): TeamMember

    @Query(
        """
            INSERT INTO team.team_members (team_id, user_id, role) 
            VALUES (:teamId, :userId, :role)
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(teamId: Long, userId: Long, role: String): TeamMember

    @Query(
        """
            UPDATE team.team_members 
            SET role = :role 
            WHERE team_id = :teamId AND user_id = :userId
            RETURNING *
        """,
        nativeQuery = true
    )
    fun updateRole(teamId: Long, userId: Long, role: String): TeamMember

    fun findByTeamIdAndUserId(teamId: Long, userId: Long): TeamMember?
    fun findByTeamId(teamId: Long): List<TeamMember>
    fun deleteByTeamIdAndUserIdIn(teamId: Long, userIds: List<Long>)
}
