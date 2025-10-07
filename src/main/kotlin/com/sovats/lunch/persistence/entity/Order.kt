package com.sovats.lunch.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(schema = "order", name = "order")
class Order (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val team: Team,

    @JoinColumn(name = "created_by_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val createdByUser: User,

    @Column(name = "status")
    val status: String,

    @Column(name = "created_at")
    val createdAt: Timestamp,

    @Column(name = "context_url")
    val contextUrl: String,
)
