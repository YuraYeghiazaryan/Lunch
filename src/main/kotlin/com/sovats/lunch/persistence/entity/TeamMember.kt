package com.sovats.lunch.persistence.entity

import com.sovats.lunch.model.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "team_members")
@IdClass(TeamMemberId::class) // composite primary key
class TeamMember(

    @Id
    @JoinColumn(name = "team_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val team: Team,

    @Id
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole
)

data class TeamMemberId(
    val teamId: Long = 0,
    val userId: Long = 0
) : Serializable
