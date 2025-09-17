package com.sovats.lunch.service

import com.sovats.lunch.persistence.entity.User
import com.sovats.lunch.persistence.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser(password: String, firstName: String, lastName: String, email: String): User {
        val user = User(
            password = password,
            firstName = firstName,
            lastName = lastName,
            email = email
        )

        return this.userRepository.save(user)
    }

    fun getUserById(id: Long): User {
        return this.userRepository.findById(id).get()
    }

    fun findUserByEmail(email: String): User? {
        return this.userRepository.findByEmail(email)
    }
}