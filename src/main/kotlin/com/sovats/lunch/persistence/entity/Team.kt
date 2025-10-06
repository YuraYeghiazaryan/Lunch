package com.sovats.lunch.persistence.entity

import com.sovats.lunch.model.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(schema = "team", name = "team")
class Team(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq_gen")
    @SequenceGenerator(
        name = "team_seq_gen",
        sequenceName = "team.team_seq",
        allocationSize = 1
    )
    val id: Long,

    @Column(name = "name")
    var name: String,

    @JoinColumn(name = "created_by_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val createdByUser: User,

    @Column(name = "created_at")
    val createdAt: Timestamp,

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    val teamMembers: MutableSet<TeamMember>,
) {
    val members: Set<Members>
        get() = teamMembers.map { Members(it.user, it.role) }.toSet()
}

data class Members(
    val user: User,
    val role: UserRole
)
