package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface OrderRepository : JpaRepository<Order, Long> {
    @Query(
        """
            INSERT INTO "order".order (team_id, created_by_user_id, context_url)
            VALUES (:teamId, :createdByUserId, :contextUrl)
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(teamId: Long, createdByUserId: Long, contextUrl: String?): Order

    @Query(
        """
            UPDATE "order"."order" 
            SET context_url = :contextUrl
            WHERE id = :orderId
            RETURNING *
        """,
        nativeQuery = true
    )
    fun updateOrderDetails(orderId: Long, contextUrl: String?): Order

    @Query(
        """
            UPDATE "order"."order" 
            SET status = :status
            WHERE id = :orderId
            RETURNING *
        """,
        nativeQuery = true
    )
    fun updateStatus(orderId: Long, status: String): Order

    @Query(
        """
        DELETE FROM "order"."order"
        WHERE id = :orderId
        RETURNING *
        """,
        nativeQuery = true
    )
    fun deleteOrderById(orderId: Long): Order
}
