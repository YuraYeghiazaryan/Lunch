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
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp

@Entity
@Table(name = "team")
class Team(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @JoinColumn(name = "created_by_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val createdByUser: User,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: Timestamp? = null,

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
