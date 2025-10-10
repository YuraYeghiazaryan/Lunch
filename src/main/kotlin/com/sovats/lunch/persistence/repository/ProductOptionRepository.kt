package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.ProductOption
import com.sovats.lunch.persistence.entity.ProductOptionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface ProductOptionRepository : JpaRepository<ProductOption, ProductOptionId> {
    @Query(
        """
            INSERT INTO  product.product_option(product_id, option_id)
            VALUES (:productId, :optionId) 
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(productId: Long, optionId: Long): ProductOption

    fun existsByProductIdAndOptionId(productId: Long, optionId: Long): Boolean
}
