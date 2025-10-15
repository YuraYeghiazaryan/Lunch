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
            INSERT INTO team.team (name, creator_id)
            VALUES (:name, :creatorId)
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(name: String, creatorId: Long): Team

    @Query(
        """
            UPDATE team.team 
            SET name = :name
            WHERE id = :id
            RETURNING *
        """,
        nativeQuery = true
    )
    fun updateTeamDetails(id: Long, name: String): Team
}
