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
@Table(schema = "`user`", name = "`user`")
class User (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "first_name")
    val firstName: String,

    @Column(name = "last_name")
    val lastName: String,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val teamMemberships: MutableSet<TeamMember>,
) {
    val teams: Set<Team>
        get() = teamMemberships.map { it.team }.toSet()
}
