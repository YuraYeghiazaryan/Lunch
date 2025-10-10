package com.sovats.lunch.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(schema = "product", name = "product_option")
@IdClass(ProductOptionId::class) // composite primary key
class ProductOption (
    @Id
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,

    @Id
    @JoinColumn(name = "option_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val option: Option,
)

data class ProductOptionId(
    val product: Long = 0,
    val option: Long = 0
) : Serializable
