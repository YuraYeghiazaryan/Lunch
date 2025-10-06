package com.sovats.lunch.persistence.repository

import com.sovats.lunch.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface UserRepository: JpaRepository<User, Long> {
    @Query(
        """
            INSERT INTO "user".user(email, password, first_name, last_name)
            VALUES (:email, :password, :firstName, :lastName)
            RETURNING * 
        """,
        nativeQuery = true
    )
    fun insert(email: String, password: String, firstName: String, lastName: String): User
    fun findUserById(id: Long): User?
    fun findByEmail(email: String): User?
}
