package com.sovats.lunch.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "`user`")
class User (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "firstName")
    val firstName: String,

    @Column(name = "lastName")
    val lastName: String,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val teamMemberships: MutableSet<TeamMember>,
) {
    val teams: Set<Team>
        get() = teamMemberships.map { it.team }.toSet()
}
