package com.sovats.lunch.persistence.entity

import com.sovats.lunch.model.UserRole
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "team_members")
@IdClass(TeamMemberId::class) // composite primary key
class TeamMember(

    @Id
    @Column(name = "team_id", nullable = false)
    val teamId: Long,

    @Id
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole
)

data class TeamMemberId(
    val teamId: Long = 0,
    val userId: Long = 0
) : Serializable