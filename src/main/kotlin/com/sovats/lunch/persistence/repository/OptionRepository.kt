package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.Option
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface OptionRepository : JpaRepository<Option, Long> {
    @Query(
        """
            INSERT INTO  product.option(name)
            VALUES (:name) 
            RETURNING *
        """,
        nativeQuery = true
    )
    fun insert(name: String): Option

    fun findByName(name: String): Option?
}
