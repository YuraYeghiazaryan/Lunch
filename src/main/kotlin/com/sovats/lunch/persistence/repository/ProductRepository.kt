package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface ProductRepository : JpaRepository<Product, Long> {
    @Query(
        """
            INSERT INTO product.product(created_by_user_id, order_id, name, url, description, quantity, item_price)
            VALUES (:createdByUserId, :orderId, :name, :url, :description, :quantity, :itemPrice)
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(createdByUserId: Long, orderId: Long, name: String, url: String?, description: String?, quantity: Int, itemPrice: Long): Product

    @Query(
        """
            UPDATE product.product
            SET name = :name, url = :url, description = :description, quantity = :quantity, item_price = :itemPrice
            WHERE id = :productId
            RETURNING *
        """,
        nativeQuery = true
    )
    fun updateProductDetails(productId: Long, name: String, url: String?, description: String?, quantity: Int, itemPrice: Long): Product

    fun findProductById(productId: Long): Product
}
