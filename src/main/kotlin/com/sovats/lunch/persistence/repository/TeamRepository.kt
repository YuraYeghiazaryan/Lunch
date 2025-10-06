package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface TeamRepository : JpaRepository<Team, Long> {
    @Query(
        """
            INSERT INTO team.team (name, created_by_user_id)
            VALUES (:name, :createdByUserId)
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(name: String, createdByUserId: Long): Team

    @Query(
        """
            UPDATE team.team 
            SET name = :name
            WHERE id = :id
            RETURNING *
        """,
        nativeQuery = true
    )
    fun updateNameTeamDetails(id: Long, name: String): Team

    fun findTeamById(id: Long): Team?
}
