package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : JpaRepository<Team, Long> {
    // TODO add insert method
    fun findTeamById(id: Long): Team?
}
