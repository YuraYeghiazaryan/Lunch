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

@Entity
@Table(schema = "product", name = "product")
class Product (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "url")
    val url: String,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "item_price")
    val itemPrice: Long,

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,

    @JoinColumn(name = "creator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val creator: User,
)
